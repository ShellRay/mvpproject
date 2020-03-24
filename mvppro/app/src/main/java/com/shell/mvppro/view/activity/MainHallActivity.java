package com.shell.mvppro.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.shell.mvppro.R;
import com.shell.mvppro.basemvp.base.BaseView;
import com.shell.mvppro.view.fragment.NewsFragment;
import com.shell.mvppro.view.fragment.RecommendFragment;
import com.shell.mvppro.view.wedgit.bottombar.BottomBar;
import com.shell.mvppro.view.wedgit.bottombar.CustomTabEntity;
import com.shell.mvppro.view.wedgit.bottombar.TabEntity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;


/**
 * @author ShellRay
 * Created  on 2020/3/19.
 * @description
 */
public class MainHallActivity extends BaseActivity implements BaseView, BottomBar.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.entrance_bar)
    BottomBar mBottomBar;
    @BindView(R.id.main_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.main_nav_view)
    NavigationView mNavigationView;

    private Fragment[] mFragments = new Fragment[4];
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles;
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_category_selected,
            R.mipmap.ic_dynamic_selected, R.mipmap.ic_communicate_selected};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_unselected, R.mipmap.ic_category_unselected,
            R.mipmap.ic_dynamic_unselected, R.mipmap.ic_communicate_unselected};

    public static final String RECOMMEND_TAG = "recommend_tag"; //大厅
    public static final String MAIN_TAG = "main_tag"; //消息
    public static final String MSG_TAG = "msg_tag"; //消息
    public static final String MINE_TAG = "mine_tag"; //我的

    private String[] tags = new String[]{RECOMMEND_TAG, MAIN_TAG, MSG_TAG,MINE_TAG};
    private FragmentManager fragmentManager;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_main_hall;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mNavigationView.setNavigationItemSelectedListener(this);
        //隐藏NavigationView右侧滚动条
        NavigationMenuView navigationMenuView = (NavigationMenuView) mNavigationView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }
        mTitles = getResources().getStringArray(R.array.main_sections);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mBottomBar.setTabEntities(mTabEntities);
        mBottomBar.setOnTabSelectedListener(this);
        initFragments();
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        RecommendFragment recommendFragment = new RecommendFragment();
        NewsFragment newsFragment = new NewsFragment();
        mFragments[FIRST] = recommendFragment;
        mFragments[SECOND] = newsFragment;
        mFragments[THIRD] =  newsFragment;
        mFragments[FOURTH] =  newsFragment;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, mFragments[FIRST],tags[FIRST]);
        transaction.commitAllowingStateLoss();
        showFragment = mFragments[FIRST];
    }

    private Fragment showFragment;
    /**
     * 切换页面的重载，优化了fragment 的切换
     */
    public void switchFragment(Fragment from, Fragment to,String tag) {
        if (from == null || to == null) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            //隐藏当前的fragment
            transaction.hide(from).add(R.id.main_container, to,tag).commitAllowingStateLoss();
        } else {
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
        showFragment = to;

    }

    @Override
    protected boolean hasCustomTitle() {
        return false;
    }

    @Override
    public void showTips(String tips) {

    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        switchFragment (mFragments[prePosition], mFragments[position],tags[position]);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
