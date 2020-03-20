package com.shell.mvppro.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.shell.mvppro.R;

import java.util.ArrayList;
import java.util.List;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 */

public class NewsFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private NewsPagerAdapter adapter;
//    private List<BaseMvpFragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"直播", "推荐", "追番", "分区", "动态", "发现"};

    @Override
    protected void isFristVisibleToUser() {

    }

    @Override
    protected int setContentLayoutResID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews(View mainView, Bundle savedInstanceState) {
//        mFragments.add(new NewsPageFragment2());
//        mFragments.add(new NewsPageFragment());
//        mFragments.add(new NewsPageFragment());
//        mFragments.add(new NewsPageFragment());
//        mFragments.add(new NewsPageFragment());
//        mFragments.add(new NewsPageFragment());
        adapter = new NewsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class NewsPagerAdapter extends FragmentPagerAdapter {

        public NewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return null;//mFragments.get(position);
        }
    }

}
