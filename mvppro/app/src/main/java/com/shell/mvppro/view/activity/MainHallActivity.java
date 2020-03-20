package com.shell.mvppro.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.shell.mvppro.R;
import com.shell.mvppro.basemvp.base.BaseView;
import com.shell.mvppro.basemvp.inject.InjectPresenter;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.bean.RecListResponse;
import com.shell.mvppro.contract.RecommendContract;
import com.shell.mvppro.presenter.RecommendPresenter;
import com.shell.mvppro.uitls.ToastUtils;
import com.shell.mvppro.view.fragment.NewsFragment;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import io.reactivex.annotations.NonNull;


/**
 * @author ShellRay
 * Created  on 2020/3/19.
 * @description
 */
public class MainHallActivity extends BaseActivity implements RecommendContract.RecommendView {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.news_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @InjectPresenter
    RecommendPresenter presenter;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_main_hall;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        NewsFragment newsFragment = new NewsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.news_container, newsFragment);
        transaction.commitAllowingStateLoss();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

//                if (id == R.id.nav_camera) {
//                    // Handle the camera action
//                } else if (id == R.id.nav_gallery) {
//
//                } else if (id == R.id.nav_slideshow) {
//
//                } else if (id == R.id.nav_manage) {
//
//                } else if (id == R.id.nav_share) {
//
//                } else if (id == R.id.nav_send) {
//
//                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        presenter.reqDataP(this,0);
    }

    @Override
    public void showTips(String tips) {

    }

    @Override
    public void updateStateView(RecListResponse<BiliAppIndex> response) {
        ToastUtils.show(response.getData().get(0).toString());
    }
}
