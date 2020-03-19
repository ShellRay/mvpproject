package com.shell.mvppro.contract;

import android.content.Context;

import com.shell.mvppro.basemvp.base.BaseView;
import com.shell.mvppro.basemvp.callback.NetworkCallback;
import com.shell.mvppro.bean.BaseBean;
import com.shell.mvppro.bean.LoginBean;
import com.shell.mvppro.bean.LoginResponse;

/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class LoginContract {

    public interface LoginPageView extends BaseView {
        void loginStateView(LoginResponse bean);
    }

    public interface LoginPresenter{
        void reqLoginP(Context context,String userId, String passPort);
    }

    public interface LoginModle  {
        void reqLoginM(Context context, String userId, String passPort, NetworkCallback callback);
    }
}
