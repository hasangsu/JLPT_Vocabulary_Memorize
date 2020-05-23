package com.example.jlpt_vocabulary_memorize.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jlpt_vocabulary_memorize.R;

public class CFragment extends Fragment {
    private int m_level = 0;

    public int getM_level() {
        return m_level;
    }

    public void setM_level(int m_level) {
        this.m_level = m_level;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // [0] Fragment 생성되어질 때.
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_jlpt_level_page, container, false);
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
        ImageView imageView = (ImageView) getView().findViewById(R.id.level_image);
        imageView.setImageResource(imageResource);
        super.onViewCreated(view, savedInstanceState);
    }
}
