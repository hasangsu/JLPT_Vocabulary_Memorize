package com.example.jlpt_vocabulary_memorize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class CVocabularyInfomationActivity extends AppCompatActivity implements View.OnClickListener {

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

        // [2] Left, Right 버튼 클릭이벤트 생성.
        Button buttonLeft = (Button) findViewById(R.id.button_left);
        Button buttonRight = (Button) findViewById(R.id.button_right);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
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

        String kanji = m_activeBocabulary.getM_kanji();
        String hiragana = m_activeBocabulary.getM_hiragana();

        if (kanji.isEmpty())
            kanji = hiragana;
        if (hiragana.isEmpty())
            hiragana = kanji;

        // [0] Kanji Text 화면에 디스플레이 Update.
        TextView kanjiTextView = (TextView) findViewById(R.id.kanji_text);
        kanjiTextView.setText(kanji);

        // [0] Kanji Text 화면에 디스플레이 Update.
        TextView hiraganaTextView = (TextView) findViewById(R.id.hiragana_text);
        hiraganaTextView.setText(hiragana);
    }

    @Override
    public void onClick(View v)
    {
        // [0] 클릭되어진 버튼 ID 검사.
        int clickId = v.getId();
        if (clickId == R.id.button_left)
        {
            // [1] 왼쪽 버튼 눌렀을 때.
            // [1-1] Activity Bocabulary의 이전 Bocabulary 구하기.
            CVocabulary prevBocabulary = getPrevBocabulary();

            // [1-2] Activity Bocabulary를 prevBocabulary 설정.
            setM_activeBocabulary(prevBocabulary);

            // [1-3] 화면 업데이트.
            inItDisplayText();
        }
        else if (clickId == R.id.button_right)
        {
            // [2] 오른쪽 버튼 눌렀을 때.
            // [2-1] Activity Bocabulary의 다음 Bocabulary 구하기.
            CVocabulary nextBocabulary = getNextBocabulary();

            // [2-2] Activity Bocabulary를 nextBocabulary 설정.
            setM_activeBocabulary(nextBocabulary);

            // [2-3] 화면 업데이트.
            inItDisplayText();
        }
        else
        {
            // 아무기능 실행안함.
        }
    }

    public CVocabulary getNextBocabulary() {
        // [0] vocabularyList 비어있으면 기능 실행 안함.
        if (m_vocabularyList.isEmpty())
            return null;

        // [1] activity Bocabulary 없으면 기능 실행 안함.
        if (m_activeBocabulary == null)
            return null;

        // [2] vocabularyList의 모든 단어 갯수 구하기(리스트 카운트).
        int listSize = m_vocabularyList.size();

        // [3] activity vocabulary가 vocabularyList에서 몇번째 index인지 구하기.
        int currentIndex = m_vocabularyList.indexOf(m_activeBocabulary);

        // [4] activity vocabulary의 index의 다음 index 구하기.
        int nextIndex = currentIndex + 1;

        // [5] next index가 모든 단어 갯수(리스트 카운트)보다 커서, 다음단어가 없을 경우 맨처음 단어로 세팅.
        if (listSize <= nextIndex)
        {
            nextIndex = 0;
        }

        // [6] 다음단어 구하여 리턴.
        CVocabulary nextVocabulary = m_vocabularyList.get(nextIndex);
        return nextVocabulary;
    }

    public CVocabulary getPrevBocabulary() {
        // [0] vocabularyList 비어있으면 기능 실행 안함.
        if (m_vocabularyList.isEmpty())
            return null;

        // [1] activity Bocabulary 없으면 기능 실행 안함.
        if (m_activeBocabulary == null)
            return null;

        // [2] vocabularyList의 모든 단어 갯수 구하기(리스트 카운트).
        int listSize = m_vocabularyList.size();

        // [3] activity vocabulary가 vocabularyList에서 몇번째 index인지 구하기.
        int currentIndex = m_vocabularyList.indexOf(m_activeBocabulary);

        // [4] activity vocabulary의 index의 이전 index 구하기.
        int prevIndex = currentIndex - 1;

        // [5] prev index가 모든 단어 갯수(리스트 카운트)보다 작아서, 이전단어가 없을 경우 맨마지막 단어로 세팅.
        if (prevIndex < 0)
        {
            prevIndex = listSize - 1;
        }

        // [6] 이전단어 구하여 리턴.
        CVocabulary nextVocabulary = m_vocabularyList.get(prevIndex);
        return nextVocabulary;
    }
}
