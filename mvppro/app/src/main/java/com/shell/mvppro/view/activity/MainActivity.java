package com.shell.mvppro.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.shell.mvppro.R;
import com.shell.mvppro.view.wedgit.WaveView;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.waveView)
    WaveView waveView;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        waveView.startAnimation();
        waveView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        },3000);
    }

    @Override
    protected boolean hasCustomTitle() {
        return false;
    }

    @Override
    public void showTips(String tips) {

    }
}
