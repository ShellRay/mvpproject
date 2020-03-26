package com.shell.mvppro.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.shell.mvppro.R;
import com.shell.mvppro.adapter.DRecyclerView;
import com.shell.mvppro.adapter.RecommendAdapter;
import com.shell.mvppro.basemvp.inject.InjectPresenter;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.bean.RecListResponse;
import com.shell.mvppro.contract.RecommendContract;
import com.shell.mvppro.presenter.RecommendPresenter;
import com.shell.mvppro.view.wedgit.RecommendIndexItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 *
 */

public class RecommendFragment extends BaseFragment implements RecommendContract.RecommendView, SwipeRefreshLayout.OnRefreshListener, DRecyclerView.DOnLoadMoreListener {

    public static final String TAG = RecommendFragment.class.getSimpleName();

    private String[] mTitles = {"直播", "推荐", "追番", "分区", "动态"};
    private List<BaseFragment> mFragments = new ArrayList<>();

    private NewsPagerAdapter adapter;

    private static int SPAN_COUNT = 2;

    @BindView(R.id.dRecyclerView)
    DRecyclerView dRecyclerView;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @InjectPresenter
    private RecommendPresenter mPresenter;

    private List<BiliAppIndex> items = new ArrayList<>();
    private RecommendAdapter mAdapter;
    private int idx;

    private void pullToRefresh(boolean isFresh) {
        if(isFresh){
            idx = 0;
            mPresenter.reqDataP(getContext(),idx);
        }else {
            List items = mAdapter.getItems();
            if (items.get(0) instanceof BiliAppIndex) {
                idx = ((BiliAppIndex) items.get(0)).getIdx() + 1;
            }
            mPresenter.reqDataP(getContext(), idx);
        }
    }


    @Override
    public void updateStateView(RecListResponse<BiliAppIndex> bean) {

        dRecyclerView.isRefresh(false);
        if(items.size() > 1){
            if(idx == 0){
                items.clear();
                mAdapter.clear();
                items.addAll(bean.getData());
            }else {
                items.addAll(bean.getData());
                mAdapter.addLists(items);
            }
        }else {
            items.addAll(bean.getData());
            mAdapter.addLists(items);
        }

    }

    @Override
    public void showTips(String tips) {

    }

    @Override
    protected void isFristVisibleToUser() {

    }

    @Override
    protected int setContentLayoutResID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews(View mainView, Bundle savedInstanceState) {
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        adapter = new NewsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Log.e("tag","position==;"+position);
                if(mAdapter.getItems().size() <= position){
                    return SPAN_COUNT;
                }else {
                    Object o = mAdapter.getItems().get(position);
                    return o instanceof BiliAppIndex ? 1 : SPAN_COUNT;
                }
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);

        dRecyclerView.setCanLoadMore(true);
        dRecyclerView.setIsEnabled(true);
        RecyclerView mRecyclerView = dRecyclerView.getmRecycler();
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.P50_BLACK));
        mRecyclerView.addItemDecoration(new RecommendIndexItemDecoration());

        mAdapter = new RecommendAdapter();
        dRecyclerView.setDAdapterAndLayoutManager(mAdapter,layoutManager);
        mAdapter.setItems(items);

        dRecyclerView.setRefreshListener(this);
        dRecyclerView.setDOnLoadMoreListener(this);
        dRecyclerView.isRefresh(true);
        pullToRefresh(true);

    }

    @Override
    public void onRefresh() {
        pullToRefresh(true);
    }

    @Override
    public void loadMore() {
        pullToRefresh(false);
    }

    @Override
    public void showTop(int position) {

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
            return mFragments.get(position);
        }
    }
}