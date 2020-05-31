package com.example.jlpt_vocabulary_memorize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class CVocabularyInfomationActivity extends AppCompatActivity {

    private ArrayList<CVocabulary> m_vocabularyList;
    private CVocabulary m_activeBocabulary = null;

    public int getM_level() {
        return m_level;
    }

    public void setM_level(int m_level) {
        this.m_level = m_level;
    }

    private int m_level;

    public ArrayList<CVocabulary> getM_vocabularyList() {
        return m_vocabularyList;
    }

    public void setM_vocabularyList(ArrayList<CVocabulary> m_vocabularyList) {
        this.m_vocabularyList = m_vocabularyList;
    }

    public CVocabulary getM_activeBocabulary() {
        return m_activeBocabulary;
    }

    public void setM_activeBocabulary(CVocabulary m_activeBocabulary) {
        this.m_activeBocabulary = m_activeBocabulary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_vocalbulary_infomation);

        // [0] Intent 정보로 세팅하기위한 함수 호출.
        initInfomation();

        // [1] Layout Widget Display Text 세팅.
        inItDisplayText();

    }

    private void initInfomation()
    {
        // [0] 전달받은 Intent의 정보로 멤버변수 및 화면 초기정보 세팅하기.
        Intent intent = getIntent();
        int level = intent.getIntExtra("level", 0);
        if (level != 0)
        {
            setM_level(level);
            ArrayList<CVocabulary> vocabularyList = ((MainActivity) MainActivity.m_context).getJLPTLevelVocabulary(m_level);

            if (!vocabularyList.isEmpty())
            {
                setM_vocabularyList(vocabularyList);
                setM_activeBocabulary(vocabularyList.get(0));
            }
        }
    }

    private void inItDisplayText()
    {
        if (m_activeBocabulary == null)
            return;

        // [0] Kanji Text 화면에 디스플레이 Update.
        TextView kanjiTextView = (TextView) findViewById(R.id.kanji_text);
        kanjiTextView.setText(m_activeBocabulary.getM_kanji());

        // [0] Kanji Text 화면에 디스플레이 Update.
        TextView hiraganaTextView = (TextView) findViewById(R.id.hiragana_text);
        hiraganaTextView.setText(m_activeBocabulary.getM_hiragana());
    }
}
