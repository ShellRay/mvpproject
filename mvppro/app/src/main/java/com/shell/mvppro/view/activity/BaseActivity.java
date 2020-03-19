package com.shell.mvppro.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shell.mvppro.R;
import com.shell.mvppro.basemvp.base.BaseView;
import com.shell.mvppro.basemvp.proxy.ActivityMvpProxyImpl;
import com.shell.mvppro.uitls.AppBarUtil;
import com.shell.mvppro.uitls.ColorUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;

/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public abstract class BaseActivity extends RxAppCompatActivity implements BaseView {

    /**
     * 是否全屏
     */
    private boolean mIsFullScreen = false;

    /**
     * 自定义根view
     */
    private ViewGroup mRootView;
    /**
     *
     */
    public Context mContext;

    /**
     * 状态栏背景颜色
     */
    private int mStatusBarViewBG = -1;

    protected Activity mActivity;
    protected boolean isLive = false;
    protected ImageButton mButtonLeft;
    protected ImageButton mButtonRight;
    protected TextView mTitleTextView;
    protected View titleLine;
    private TextView mRightText;

    private ActivityMvpProxyImpl activityMvpProxy;

    private boolean isCreated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //guiping FIXBUG 红音_V1.3.1HYIN-787  解决 app在后台 ， 然后禁止位置权限 在此打开 app奔溃的问题
        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        createProxy();
        if (null != getSupportActionBar()) {
            getSupportActionBar().hide();
        }
        isLive = true;

        //homeReceiver = new HomeReceiver();
//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        registerReceiver(homeReceiver,intentFilter);
        mActivity = this;
        ActivityStack.add(mActivity);

        mContext = getApplicationContext();
        mStatusBarViewBG = ColorUtil.parserColor(ContextCompat.getColor(mContext, R.color.white));
        mRootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.base_layout, null);
        preInitStatusBar();
        View view = LayoutInflater.from(this).inflate(setContentLayoutResID(), null);


        if (hasCustomTitle() && mRootView != null) {
            addChildView(mRootView, view, -1, -1);
            setContentView(mRootView);
            initStatusBar(mRootView);

            if (!hasTitleLineView()) {
                titleLine.setVisibility(View.GONE);
            }
        } else {
            super.setContentView(view);
            initStatusBar(view);
        }
        ButterKnife.bind(this);

        //初始化view相关数据
        initViews(savedInstanceState);
        if (hasCustomTitle()) {
            AppBarUtil.setStatusBarMode(this, true, R.color.white);
        }

        isCreated = true;
    }

    protected boolean isCreated() {
        return isCreated;
    }

    private void createProxy() {
        if(activityMvpProxy == null){
            activityMvpProxy = new ActivityMvpProxyImpl(this);
        }
        activityMvpProxy.bindAndCreatePresenter();
    }

    /**
     * 初始化状态栏
     *
     * @param view
     */
    private void initStatusBar(View view) {
        if (!mIsFullScreen) {
            AppBarUtil.initBar(this.getWindow());
            boolean isAddStatusBar = AppBarUtil.isAddStatusBar();
            //添加状态栏
            addStatusBar(view, isAddStatusBar);
        }
    }


    /**
     * 添加自定义状态栏
     *
     * @param view
     * @param isAddStatusBar 是否添加状态栏
     */
    private void addStatusBar(View view, boolean isAddStatusBar) {
        View statusBarView = view.findViewById(R.id.status_bar_view);
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
     * 添加子view
     *
     * @param parentView 父view
     * @param childView  子view
     */
    private void addChildView(ViewGroup parentView, View childView, int viewWidth, int viewHeight) {

        if (parentView instanceof ConstraintLayout) {

            ConstraintLayout.LayoutParams clp = new ConstraintLayout.LayoutParams(viewWidth, viewHeight);
            parentView.addView(childView, clp);
        } else if (parentView instanceof LinearLayout) {


            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(viewWidth, viewHeight);
            parentView.addView(childView, llp);
        } else if (parentView instanceof RelativeLayout) {


            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(viewWidth, viewHeight);
            parentView.addView(childView, rlp);
        } else if (parentView instanceof FrameLayout) {

            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(viewWidth, viewHeight);
            parentView.addView(childView, flp);

        } else if (parentView instanceof ViewGroup) {

            ViewGroup.LayoutParams vplp = new ViewGroup.LayoutParams(viewWidth, viewHeight);
            parentView.addView(childView, vplp);
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //解决app 去后台设置中 设置权限相关的开关后。重新打开app 崩溃的问题。
        try {
            super.onRestoreInstanceState(savedInstanceState);
        } catch (Exception e) {
            savedInstanceState = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isLive = true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        isLive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isLive = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        isLive = false;
    }

    @Override
    protected void onDestroy() {

        isLive = false;
        ActivityStack.remove(mActivity);
//        EventBusManager.getInstance().unregister(this);
        super.onDestroy();
        activityMvpProxy.unbindPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isLive = true;
    }

    @Override
    public void finish() {
//        EventBusManager.getInstance().unregister(this);
        isLive = false;

        if (isCancelRequestWhenFinish()) {
//            OKHttpManager.getInstance().cancleRequest();
        }

        super.finish();
    }

    /**
     * 设置状态栏背景颜色
     *
     * @param statusBarViewBG
     */
    public void setStatusBarViewBG(int statusBarViewBG) {
        this.mStatusBarViewBG = statusBarViewBG;
    }

    public void setFullScreen(boolean isFullScreen) {
        this.mIsFullScreen = isFullScreen;
    }

    /**
     * 初始化view之前
     */
    protected void preInitStatusBar() {

    }

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
    protected abstract void initViews(Bundle savedInstanceState);


    /**
     * 设置 rootview
     *
     * @param rootView
     */
    protected void setRootView(ViewGroup rootView) {
        this.mRootView = rootView;
    }

    protected boolean hasCustomTitle() {
        return true;
    }

    /**
     * 是否展示标题下放的线
     */
    protected boolean hasTitleLineView() {
        return true;
    }

    /**
     * finish的时候是否取消http请求
     */
    protected boolean isCancelRequestWhenFinish() {
        return true;
    }

    public void onLeftBtnClick(View v) {
        finish();
    }

    public void onRightBtnClick(View v) {

    }

    @Override
    public void setTitle(int titleId) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(titleId);
        }
    }

    public void setTitle(CharSequence title) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
    }

    public void setTitle(CharSequence title, boolean isBoln) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
            if (isBoln)
                mTitleTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

    }


    protected ImageButton setRightBtnDrawable(int resid) {
        if (mButtonRight != null) {
            mButtonRight.setVisibility(View.VISIBLE);
            mRightText.setVisibility(View.GONE);
            mButtonRight.setImageResource(resid);
            return mButtonRight;
        }
        return null;
    }

    protected TextView setRightBtnDrawable(String resid) {
        if (!TextUtils.isEmpty(resid)) {
            mRightText.setVisibility(View.VISIBLE);
            mButtonRight.setVisibility(View.GONE);
            mRightText.setText(resid);
            return mRightText;
        }
        return null;
    }

    protected TextView setRightTextColor(int resid) {
        if (mRightText != null) {
            mRightText.setTextColor(getResources().getColor(resid));
            return mRightText;
        }
        return null;
    }

    protected ImageButton setLeftBtnDrawable(int resid) {
        if (mButtonLeft != null) {
            mButtonLeft.setVisibility(View.VISIBLE);
            mButtonLeft.setImageResource(resid);
            return mButtonLeft;
        }
        return null;
    }

    protected void hideLeftButton() {
        if (mButtonLeft != null)
            mButtonLeft.setVisibility(View.GONE);
    }

    protected void hideRightButton() {
        if (mButtonRight != null)
            mButtonRight.setVisibility(View.GONE);
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if (hasCustomTitle()) {

            mButtonLeft = (ImageButton) view.findViewById(R.id.title_button_left);
            mButtonRight = (ImageButton) view.findViewById(R.id.title_button_right);
            mRightText = (TextView) view.findViewById(R.id.title_button_right_text);
            mTitleTextView = (TextView) view.findViewById(R.id.title_text);
            titleLine = view.findViewById(R.id.titleLine);

            mButtonLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftBtnClick(v);
                }
            });
            mButtonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightBtnClick(v);
                }
            });
            mRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightBtnClick(v);
                }
            });
        }
    }

    public void onCalling() {

    }

    public void onCallingIdle() {

    }

    //字体大小不随系统设置
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}