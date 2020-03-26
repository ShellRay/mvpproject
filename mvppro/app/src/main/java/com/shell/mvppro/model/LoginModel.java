package com.shell.mvppro.model;

import android.content.Context;

import com.shell.mvppro.basemvp.base.BaseModel;
import com.shell.mvppro.basemvp.callback.NetworkCallback;
import com.shell.mvppro.bean.LoginBean;
import com.shell.mvppro.bean.LoginResponse;
import com.shell.mvppro.contract.LoginContract;
import com.shell.mvppro.network.apinet.RetrofitHelper;
import com.shell.mvppro.network.common.ResponseObserver;
import com.shell.mvppro.uitls.RxUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class LoginModel extends BaseModel implements LoginContract.LoginModle {

    @Override
    public void reqLoginM(Context context,String userId, String passPort, NetworkCallback callback) {
        put(LoginBean.class.hashCode(),callback);
        boolean beforeLogin = verifyBeforeLogin(userId,passPort);
        if(!beforeLogin){
            get(LoginBean.class.hashCode()).failure(verifyMsg);
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("passPort",passPort);
        RetrofitHelper.getApiService()
                .login(map)
                .compose(RxUtil.rxSchedulerHelper((RxAppCompatActivity)context, true))
                .subscribe(new ResponseObserver<LoginResponse>() {

                    @Override
                    public void onSuccess(LoginResponse response) {
                        get(LoginBean.class.hashCode()).onSuccess(response);
                    }

                    @Override
                    public void onFail(String message) {
                        get(LoginBean.class.hashCode()).failure(message);
                    }
                });

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
