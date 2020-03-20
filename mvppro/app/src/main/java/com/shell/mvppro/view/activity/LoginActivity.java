package com.shell.mvppro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shell.mvppro.R;
import com.shell.mvppro.basemvp.inject.InjectPresenter;
import com.shell.mvppro.bean.BaseBean;
import com.shell.mvppro.bean.LoginBean;
import com.shell.mvppro.bean.LoginResponse;
import com.shell.mvppro.contract.LoginContract;
import com.shell.mvppro.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class LoginActivity extends BaseActivity implements LoginContract.LoginPageView {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_passcode)
    EditText etPasscode;
    @BindView(R.id.tv_login_way_change)
    TextView tvLoginWayChange;
    @BindView(R.id.btn_login)
    TextView btnLogin;

    @InjectPresenter
    LoginPresenter presenter;
    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        presenter.reqLoginP(this,etUsername.getText().toString(),etPasscode.getText().toString());
    }


    @Override
    public void loginStateView(LoginResponse bean) {
        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTips(String tips) {
//        Toast.makeText(this,tips,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this,MainHallActivity.class));
    }

    @Override
    protected boolean hasCustomTitle() {
        return false;
    }
}
