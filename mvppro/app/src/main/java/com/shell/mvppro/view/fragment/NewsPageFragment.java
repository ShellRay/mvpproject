package com.shell.mvppro.view.fragment;


import android.os.Bundle;
import android.view.View;

import com.shell.mvppro.R;
import com.shell.mvppro.basemvp.inject.InjectPresenter;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.bean.RecListResponse;
import com.shell.mvppro.contract.RecommendContract;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 *
 */

public class NewsPageFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

//    private CommonAdapter mAdapter;


//    @Override
//    public void onLoadMore(List<WeiXinJingXuanBean.NewsList> list) {
////        mAdapter.addItems(list);
////        mAdapter.loadMoreComplete();
//        mAdapter.showNoMore();
//    }

//    @Override
//    public void setRefreshing() {
//        mRefreshLayout.setRefreshing(true);
//    }

    @Override
    protected void isFristVisibleToUser() {

    }

    @Override
    protected int setContentLayoutResID() {
        return R.layout.fragment_page_news;
    }

    @Override
    protected void initViews(View mainView, Bundle savedInstanceState) {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                presenter.loadData();
            }
        });
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAdapter = new CommonAdapter();
//        mAdapter.register(WeiXinJingXuanBean.NewsList.class, new NewsItemViewBinder(getContext()));
//        mAdapter.setOnLoadMoreListener(new DefaultAdapterWrapper.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                mPresenter.loadMore();
//            }
//        });
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showTips(String tips) {

    }
}
