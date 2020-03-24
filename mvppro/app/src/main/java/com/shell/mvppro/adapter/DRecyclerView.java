package com.shell.mvppro.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 */

public class DRecyclerView extends LinearLayout {

    private DRecyclerView mDRecyclerView = this;

    public SwipeRefreshLayout getmSwipeLayout() {
        return mSwipeLayout;
    }

    private SwipeRefreshLayout mSwipeLayout;

    public RecyclerView getmRecycler() {
        return mRecycler;
    }

    private RecyclerView mRecycler;

    private boolean isEnabled = false;
    private boolean isRefreshing;
    private LayoutParams mLayoutParams;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String LOADING = "loading";
    public static final String LOAD_COMPLETE = "load_complete";
    public static final String LOAD_END = "load_end";

    public void setLoadState(String loadState) {
        this.loadState = loadState;
    }

    private String loadState = LOAD_COMPLETE;

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    private boolean canLoadMore = true;

    public DRecyclerView(Context context) {
        super(context);
        init();
    }

    public DRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setOrientation(VERTICAL);
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(mLayoutParams);


        mSwipeLayout = new SwipeRefreshLayout(getContext());
        mSwipeLayout.setEnabled(isEnabled);
        mSwipeLayout.setColorSchemeColors(Color.BLACK);
        mSwipeLayout.setLayoutParams(mLayoutParams);

        mRecycler = new RecyclerView(getContext());
        SwipeRefreshLayout.LayoutParams params = new SwipeRefreshLayout.LayoutParams(SwipeRefreshLayout.LayoutParams.MATCH_PARENT, SwipeRefreshLayout.LayoutParams.WRAP_CONTENT);
        mRecycler.setLayoutParams(params);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.addOnScrollListener(mOnScrollListener);
        SimpleItemAnimator simpleItemAnimator = (SimpleItemAnimator) mRecycler.getItemAnimator();
        simpleItemAnimator.setSupportsChangeAnimations(false);

        mSwipeLayout.addView(mRecycler);

        addView(mSwipeLayout);

    }


    public void setDAdapterAndLayoutManager(DAdapter adapter, RecyclerView.LayoutManager manager) {
        manager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(adapter);
    }

    /**
     * 设置是否允许刷新
     */
    public void setIsEnabled(boolean enabled) {
        this.isEnabled = enabled;
        if(!isEnabled){
            mRecycler.setOverScrollMode(OVER_SCROLL_NEVER);
        }
        mSwipeLayout.setEnabled(isEnabled);
    }

    /**
     * 设置是否启动刷新
     */
    public void isRefresh(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
        mSwipeLayout.setRefreshing(isRefreshing);
    }

    public void setRefreshListener(final SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeLayout.setEnabled(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(true);
                listener.onRefresh();
            }
        });
    }

    public interface DOnLoadMoreListener {
        void loadMore();

        void showTop(int position);
    }

    private DOnLoadMoreListener mLoadMoreListener;

    public void setDOnLoadMoreListener(DOnLoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    /**
     * 滑动监听 默认当滑动到最后一项时加载更多
     */
//    private int scrollDy = 0;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            int i = recyclerView.computeVerticalScrollExtent();
            int j = recyclerView.computeVerticalScrollOffset();
            int h = recyclerView.computeVerticalScrollRange();
//            Log.e("recycler_scroll", "i=" + i + "  j=" + j + "   h=" + h);
            if (i + j >= h && h > 0 && j > 0) {
                //当此页的item都刚好完全显示的时候，再上滑不会回调onScroll方法，所以不需要这个判断条件
//                if (scrollDy > 0) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (canLoadMore && loadState == LOAD_COMPLETE) {
                            if (mLoadMoreListener != null) {
                                mLoadMoreListener.loadMore();
                            }
                        }
                    }
//                }
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            Log.e("recycler_scroll", "dy=  " + dy);

//            scrollDy = dy;
            int showTopPosition = -1;
            int firstvisPosition = -1;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                //获取最后一个完全显示的ItemPosition
                int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
                showTopPosition = getMaxElem(lastVisiblePositions);

            } else if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager manager = (GridLayoutManager) layoutManager;
                showTopPosition = manager.findLastVisibleItemPosition();
            } else {
                LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                showTopPosition = manager.findLastVisibleItemPosition();
            }
            if (mLoadMoreListener != null) {
                mLoadMoreListener.showTop(showTopPosition);
            }
            Log.e("DRecycler",dy+"");
            if(dy > 0){//向上滑动
                if(!recyclerView.canScrollVertically(1)){//滑动到底部
                    if(onRecyclerScrollListener != null){
                        onRecyclerScrollListener.onScrollToBottom();
                        Log.e("DRecycler",dy+"scrolltobottom");
                    }
                }else{
                    if(onRecyclerScrollListener != null){
                        onRecyclerScrollListener.onScrollUp();
                    }
                }
            }else{
                if(!recyclerView.canScrollVertically(-1)){//滑动到顶部
                    if(onRecyclerScrollListener != null){
                        onRecyclerScrollListener.onScrollToTop();
                        Log.e("DRecycler",dy+"scrolltotop");
                    }
                }else{
                    if(onRecyclerScrollListener != null){
                        onRecyclerScrollListener.onScrollDown();
                    }
                }
            }
        }
    };

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

    private OnRecyclerScrollListener onRecyclerScrollListener;
    public interface OnRecyclerScrollListener{
        void onScrollUp();
        void onScrollDown();
        void onScrollToTop();
        void onScrollToBottom();
    }
    public void setOnRecyclerScrollListener(OnRecyclerScrollListener listener){
        this.onRecyclerScrollListener = listener;
    }

    /**
     * 包装滚动监听，按需实现方法即可
     */
    public static class WrapperOnRecyclerScrollListener implements OnRecyclerScrollListener{

        /**
         * 向上滚动
         */
        @Override
        public void onScrollUp() {

        }

        /**
         * 向下滚动
         */
        @Override
        public void onScrollDown() {

        }

        /**
         * 滚动到顶部
         */
        @Override
        public void onScrollToTop() {

        }

        /**
         * 滚动到底部
         */
        @Override
        public void onScrollToBottom() {

        }
    }
}
