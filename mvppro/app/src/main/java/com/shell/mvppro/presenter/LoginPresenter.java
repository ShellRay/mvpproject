package com.shell.mvppro.presenter;

import android.content.Context;

import com.shell.mvppro.basemvp.base.BasePresenter;
import com.shell.mvppro.basemvp.callback.NetworkCallback;
import com.shell.mvppro.basemvp.inject.InjectModel;
import com.shell.mvppro.bean.LoginResponse;
import com.shell.mvppro.contract.LoginContract;
import com.shell.mvppro.model.LoginModel;

import java.util.regex.Pattern;

/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginPageView> implements LoginContract.LoginPresenter {

    @InjectModel
    LoginModel model;

    @Override
    public void reqLoginP(Context context,String userId, String passPort) {

            model.reqLoginM(context,userId, passPort, new NetworkCallback<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse baseBean) {
                    getView().loginStateView(baseBean);
                }

                @Override
                public void failure(String msg) {
                    getView().showTips(msg);
                }
            });
    }

}
