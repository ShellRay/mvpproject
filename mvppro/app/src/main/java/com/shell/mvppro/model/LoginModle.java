package com.shell.mvppro.model;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.shell.mvppro.basemvp.base.BaseModel;
import com.shell.mvppro.basemvp.callback.NetworkCallback;
import com.shell.mvppro.bean.LoginBean;
import com.shell.mvppro.bean.LoginResponse;
import com.shell.mvppro.contract.LoginContract;
import com.shell.mvppro.network.HttpFunction;
import com.shell.mvppro.network.apinet.RetrofitHelper;
import com.shell.mvppro.network.common.ResponseObserver;
import com.shell.mvppro.uitls.RxUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class LoginModle extends BaseModel implements LoginContract.LoginModle {

    @Override
    public void reqLoginM(Context context,String userId, String passPort, NetworkCallback callback) {
        put(LoginBean.class.hashCode(),callback);
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
                        super.onFail(message);

                    }
                });

//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        String url = requestUrl("http://www.baidu.com", new HashMap<String, String>());
//                        try {
//                            String result = HttpFunction.get(url);
//                            Looper.prepare();
//                            if(!TextUtils.isEmpty(result)){
//
//                            }else {
//                                get(LoginBean.class.hashCode()).failure("登录失败");
//                            }
//                            Looper.loop();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        ).start();

    }

    private static String requestUrl(String url, Map<String, String> params) {

        StringBuffer builder = new StringBuffer();
        builder.append(url);
        builder.append("?");

        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> en = iterator.next();
            builder.append(en.getKey());
            builder.append("=");
            builder.append(en.getValue());

            if (iterator.hasNext()) {
                builder.append("&");
            }
        }

        return builder.toString();

    }
}
