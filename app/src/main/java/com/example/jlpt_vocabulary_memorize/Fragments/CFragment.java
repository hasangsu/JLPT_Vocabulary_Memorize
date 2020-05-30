package com.example.jlpt_vocabulary_memorize.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jlpt_vocabulary_memorize.CVocabulary;
import com.example.jlpt_vocabulary_memorize.CVocalbularyInfomationActivity;
import com.example.jlpt_vocabulary_memorize.R;

import java.util.ArrayList;

public class CFragment extends Fragment {
    private int m_level = 0;
    private ArrayList<CVocabulary> vocabularyList;

    public int getM_level() {
        return m_level;
    }

    public void setM_level(int m_level) {
        this.m_level = m_level;
    }

    public ArrayList<CVocabulary> getVocabularyList() {
        return vocabularyList;
    }

    public void setVocabularyList(ArrayList<CVocabulary> vocabularyList) {
        this.vocabularyList = vocabularyList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // [0] Fragment 생성되어질 때 View 생성.
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_jlpt_level_page, container, false);

        // [1] 생성된 View에 ImageButton을 찾아와서 Click Event 연결시키기.
        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.level_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switch (v.getId())
                {
                    // [1-1] 클릭된 버튼의 종류에 따라서 Switch Case 처리.
                    case R.id.level_image :
                    {
                        Intent intent = new Intent(getActivity(), CVocalbularyInfomationActivity.class);
                        startActivity(intent);

//                        String displayText = "JLPT " + String.valueOf(m_level) + "급을 선택하셨습니다. 레벨 이동.";
//                        Toast.makeText(getContext(), displayText, Toast.LENGTH_SHORT).show();
                    } break;
                    default : break;
                }
            }
    });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // [0] Fragment UI TextView Update.
        int level = m_level;
        int imageResource = 0;
        String displayText = "";
        switch (m_level)
        {
            case 1 :
            {
                imageResource = R.mipmap.jlpt_n1;
            } break;
            case 2 :
            {
                imageResource = R.mipmap.jlpt_n2;
            } break;
            case 3 :
            {
                imageResource = R.mipmap.jlpt_n3;
            } break;
            case 4 :
            {
                imageResource = R.mipmap.jlpt_n4;
            } break;
            case 5 :
            {
                imageResource = R.mipmap.jlpt_n5;
            } break;
            default : return;
        }

        // [1] 화면에 디스플레이 되어질 TextView Update.
        displayText = "JLPT " + String.valueOf(level) + "급";
        TextView textView = (TextView) getView().findViewById(R.id.level_text);
        textView.setText(displayText);

        // [2] 화면에 디스플레이 ImageView Update.
        ImageButton imageButton = (ImageButton) getView().findViewById(R.id.level_image);
        imageButton.setImageResource(imageResource);

        super.onViewCreated(view, savedInstanceState);
    }


}
