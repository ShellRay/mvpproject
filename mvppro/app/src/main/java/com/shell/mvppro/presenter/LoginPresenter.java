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
        boolean beforeLogin = verifyBeforeLogin(userId,passPort);
        if(beforeLogin) {
            model.reqLoginM(context,userId, passPort, new NetworkCallback<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse baseBean) {
                    getView().loginStateView(baseBean);
                }

                @Override
                public void failure(String msg) {

                }
            });
        }else {
            getView().showTips(verifyMsg);
        }

    }
    String verifyMsg;
    private boolean verifyBeforeLogin(String username, String password) {
        boolean isEmpty = isEmpty(username) || isEmpty(password);
        boolean isValid = isValid(username) && isValid(password);
        if (isEmpty) {
            verifyMsg = "请输入帐号或密码";
            return false;
        }
        if (isValid) {
            return true;
        }
        verifyMsg = "帐号或密码错误";
        return false;
    }


    private boolean isValid(String s) {
        return Pattern.compile("^[A-Za-z0-9]{3,20}+$").matcher(s).matches();
    }

    private boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
