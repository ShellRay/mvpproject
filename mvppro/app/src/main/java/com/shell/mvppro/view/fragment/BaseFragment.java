package com.shell.mvppro.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shell.mvppro.R;
import com.shell.mvppro.uitls.AppBarUtil;
import com.shell.mvppro.uitls.ColorUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;


/**
 */

public abstract class BaseFragment extends RxFragment {
    /**
     * 是否是第一次可视
     */
    private boolean mIsFristVisibleToUser;

    /**
     *
     */
    public Context mContext;

    /**
     * 状态栏背景颜色
     */
    private int mStatusBarViewBG = -1;

    /**
     * 是否添加
     */
    private boolean isAddStatusBarView = true;


    /**
     * 内容布局
     */
    private ViewStub mContentContainer;

    private RefreshListener mRefreshListener;

    /**
     * 标题视图
     */
    private int mTitleViewId = R.layout.layout_title;

    /**
     * 初始化
     */
    private void init() {
        this.mIsFristVisibleToUser = false;
        this.mContext = getContext();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            savedInstanceState = null;
        }

        init();
        mStatusBarViewBG = ColorUtil.parserColor(ContextCompat.getColor(mContext, R.color.white));
        preInitStatusBar();
        View mainView = inflater.inflate(R.layout.fragment_base, container, false);
        //添加主布局
        mContentContainer = mainView.findViewById(R.id.viewstub_content_container);
        mContentContainer.setLayoutResource(setContentLayoutResID());
        mContentContainer.inflate();
        mContentContainer.setVisibility(View.GONE);
        ButterKnife.bind(this,mainView);
        //添加titleview
        View titleView = inflater.inflate(mTitleViewId, container, false);
        LinearLayout titleViewLL =  mainView.findViewById(R.id.title_view_parent);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, -1);
        titleViewLL.addView(titleView,llp);

        //初始化view相关数据
        initViews(mainView, savedInstanceState);
        if (isAddStatusBarView) {
            initStatusBar(mainView);
        }

        //初始化是否可视
        if (!mIsFristVisibleToUser && getUserVisibleHint()) {
            mIsFristVisibleToUser = true;
            isFristVisibleToUser();
        }
        return mainView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 初始化状态栏
     *
     * @param
     */
    private void initStatusBar(View mainView) {
        boolean isAddStatusBar = AppBarUtil.isAddStatusBar();
        //添加状态栏
        addStatusBar(mainView, isAddStatusBar);
    }

    /**
     * 添加状态栏
     *
     * @param isAddStatusBar
     */
    private void addStatusBar(View mainView, boolean isAddStatusBar) {
        View statusBarView = mainView.findViewById(R.id.status_bar_view);
        if (statusBarView == null) return;
        if (!isAddStatusBar) {
            statusBarView.setVisibility(View.GONE);
            return;
        }

        ViewParent parentView = statusBarView.getParent();
        int statusBarViewHeight = AppBarUtil.getStatusBarHeight(mContext);

        if (parentView instanceof ConstraintLayout) {
            ConstraintLayout.LayoutParams clp = new ConstraintLayout.LayoutParams(-1, statusBarViewHeight);
            statusBarView.setLayoutParams(clp);
        } else if (parentView instanceof LinearLayout) {
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, statusBarViewHeight);
            statusBarView.setLayoutParams(llp);
        } else if (parentView instanceof RelativeLayout) {
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1, statusBarViewHeight);
            statusBarView.setLayoutParams(rlp);
        } else if (parentView instanceof FrameLayout) {
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(-1, statusBarViewHeight);
            statusBarView.setLayoutParams(flp);
        } else if (parentView instanceof ViewGroup) {
            ViewGroup.LayoutParams vplp = new ViewGroup.LayoutParams(-1, statusBarViewHeight);
            statusBarView.setLayoutParams(vplp);
        }

        statusBarView.setVisibility(View.VISIBLE);
        statusBarView.setBackgroundColor(mStatusBarViewBG);
    }

    /**
     * 初始化view之前
     */
    protected void preInitStatusBar() {

    }



    /**
     * 设置状态栏背景颜色
     *
     * @param statusBarViewBG
     */
    public void setStatusBarViewBG(int statusBarViewBG) {
        this.mStatusBarViewBG = statusBarViewBG;
    }

    public void setTitleViewId(int mTitleViewId) {
        this.mTitleViewId = mTitleViewId;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!mIsFristVisibleToUser && getUserVisibleHint() && getView() != null) {
            mIsFristVisibleToUser = true;
            isFristVisibleToUser();
        }
    }

    /**
     * 视图可见，只执行一次
     */
    protected abstract void isFristVisibleToUser();

    /**
     * 设置主界面内容视图
     *
     * @return
     */
    protected abstract int setContentLayoutResID();

    /**
     * 初始化view视图
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(View mainView, Bundle savedInstanceState);


    public interface RefreshListener {
        void refresh();
    }

    public void setRefreshListener(RefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }
}
