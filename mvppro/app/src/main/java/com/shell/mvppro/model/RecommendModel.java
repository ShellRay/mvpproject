package com.shell.mvppro.model;

import android.content.Context;

import com.shell.mvppro.basemvp.base.BaseModel;
import com.shell.mvppro.basemvp.callback.NetworkCallback;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.bean.LoginBean;
import com.shell.mvppro.bean.LoginResponse;
import com.shell.mvppro.bean.RecListResponse;
import com.shell.mvppro.contract.RecommendContract;
import com.shell.mvppro.network.apinet.ApiHelper;
import com.shell.mvppro.network.apinet.RetrofitHelper;
import com.shell.mvppro.network.common.ResponseObserver;
import com.shell.mvppro.uitls.DateUtil;
import com.shell.mvppro.uitls.RxUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author ShellRay
 * Created  on 2020/3/19.
 * @description
 */
public class RecommendModel extends BaseModel implements RecommendContract.RecommendModle {
    private static final int LOGIN_EVENT_NORMAL = 0;
    private static final int LOGIN_EVENT_INITIAL = 1;
    private static final String OPEN_EVENT_NULL = "";
    private static final int STYLE = 2;

    int loginEvent = LOGIN_EVENT_NORMAL;
    String openEvent = OPEN_EVENT_NULL;
    boolean pull = false;

    @Override
    public void reqDataM(Context context, int index, NetworkCallback callback) {
        put(RecListResponse.class.hashCode(),callback);
        RetrofitHelper.getApiService()
                .getIndex(ApiHelper.APP_KEY,
                        ApiHelper.BUILD,
                        index,
                        loginEvent,
                        ApiHelper.MOBI_APP,
                        ApiHelper.NETWORK_WIFI,
                        openEvent,
                        ApiHelper.PLATFORM,
                        pull,
                        STYLE,
                        DateUtil.getSystemTime())
                .compose(RxUtil.rxSchedulerHelper((RxAppCompatActivity)context, true))
                .subscribe(new ResponseObserver<RecListResponse<BiliAppIndex>>() {

                    @Override
                    public void onSuccess(RecListResponse<BiliAppIndex> response) {
                        get(RecListResponse.class.hashCode()).onSuccess(response);
                    }

                    @Override
                    public void onFail(String message) {
                        get(LoginBean.class.hashCode()).failure(message);
                    }
                });
    }
}
