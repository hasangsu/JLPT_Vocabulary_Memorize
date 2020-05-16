package com.example.jlpt_vocabulary_memorize;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.jlpt_vocabulary_memorize.CVocabularyParser;

import java.io.File;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static CVocabularyDBHelper m_vocabularyDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switch (v.getId()) {
                    case R.id.button1:
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setTitle("버튼 클릭 테스트 다이얼로그");
//                        builder.setMessage("Export DB Button을 클릭하셨습니다.");
//                        builder.setPositiveButton("예", null);
//                        builder.setNegativeButton("아니오", null);
//                        builder.setNeutralButton("취소", null);
//                        builder.create().show();
                        break;
                }
            }
        };

        findViewById(R.id.button1).setOnClickListener(listener);

        // 아래 실행안하도록 함.
        if (false)
        {
            // 처음 실행 시, Vocabulary Create DB (JLPT 1 ~ 5).
            m_vocabularyDBHelper = new CVocabularyDBHelper(MainActivity.this);

            // XML 파싱, Vocabulary Insert DB (JLPT 1 ~ 5).
            for (int level = 0 ; level < 5; level++)
            {
                int resourceId = R.raw.jlpt1;
                resourceId += level;

                m_vocabularyDBHelper.setM_jlptLevel(level + 1);
                m_vocabularyDBHelper.setM_jlptNo(1);
                vocabulary_parsing(super.getResources(), resourceId);

                // XML 파싱, Vocabulary 리스트 결과 확인.
                ArrayList<CVocabulary> vocabularyList = new ArrayList<> ();
                vocabularyList = m_vocabularyDBHelper.requestCheckAllVocabulary();
            }
        }
    }

    public static void vocabulary_parsing(Resources resources, int level) {
        // vocabulary parsing class 생성.
        CVocabularyParser vocabularyParser = new CVocabularyParser();
        vocabularyParser.vocabulary_file_parsing(resources, level, m_vocabularyDBHelper);
    }

}
