package com.example.jlpt_vocabulary_memorize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;

import com.example.jlpt_vocabulary_memorize.CVocabularyParser;
import com.example.jlpt_vocabulary_memorize.Fragments.CFragment;

import java.io.File;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static CVocabularyDBHelper m_vocabularyDBHelper;
    private ViewPager m_viewPager;
    private PagerAdapter m_pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [0] DB File 생성.
        m_vocabularyDBHelper = new CVocabularyDBHelper(MainActivity.this);

        // [1] 어플리케이션 최초실행 여부 확인.
        boolean isFirst = checkAppFirstExecute();
        if (isFirst)
        {
            // [1-1] 처음 실행 시, Vocabulary Create DB (JLPT 1 ~ 5).

            // [1-2] XML 파싱, Vocabulary Insert DB (JLPT 1 ~ 5).
            int resourceId = R.raw.jlpt1;
            for (int level = 1; level <= 5; level++)
            {
                if (level != 1)
                    resourceId = resourceId + 1;

                // [1-3] JLPT 급수별로 급수 및 단어 순서 세팅해주기.
                m_vocabularyDBHelper.setM_jlptLevel(level);
                m_vocabularyDBHelper.setM_jlptNo(1);
                vocabulary_parsing(super.getResources(), resourceId);
            }
        }

        // [2] 처음 실행되면 Splash 이후에 보여질 JLPT 급수 선택 리스트 생성.
        List<Fragment> fragmentList = new ArrayList<>();
        for (int index = 5; 1 <= index; index--) {
            // [2-1] DB에 저장된 급수별 단어리스트를 가져오기.
            ArrayList<CVocabulary> vocabularyList = new ArrayList<>();
            vocabularyList = m_vocabularyDBHelper.requestCheckJLPTLevelVocabulary(index);

            // [2-2] 화면에서 선택할 수 있는 JLPT 급수 생성 및 텍스트 세팅.
            CFragment fragment = new CFragment();
            fragment.setM_level(index);
            fragment.setM_vocabularyList(vocabularyList);

            // [2-3] 생성한 JLPT 급수를 fragmentList에 더함.
            fragmentList.add(fragment);
        }

        // [2-4] Main Activity에서 사용할 viewPager 생성 및 세팅.
        m_viewPager = findViewById(R.id.viewPager);
        m_pageAdapter = new CViewPagerAdapter(getSupportFragmentManager(), fragmentList);

        m_viewPager.setAdapter(m_pageAdapter);
    }

    public static void vocabulary_parsing(Resources resources, int level) {
        // vocabulary parsing class 생성.
        CVocabularyParser vocabularyParser = new CVocabularyParser();
        vocabularyParser.vocabulary_file_parsing(resources, level, m_vocabularyDBHelper);
    }

    public boolean checkAppFirstExecute()
    {
        SharedPreferences pref = getSharedPreferences("IsFirst" , Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if (!isFirst)
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        }

        return !isFirst;
    }

}
