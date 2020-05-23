package com.example.jlpt_vocabulary_memorize;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

// Main Activity의 JLPT 급수별 슬라이드 조작 Adapter.
public class CViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> m_fragmentList;

    public CViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.m_fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return m_fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return m_fragmentList.size();
    }
}
