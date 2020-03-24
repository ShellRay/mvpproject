package com.shell.mvppro.presenter;

import android.content.Context;

import com.shell.mvppro.basemvp.base.BasePresenter;
import com.shell.mvppro.basemvp.callback.NetworkCallback;
import com.shell.mvppro.basemvp.inject.InjectModel;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.bean.RecListResponse;
import com.shell.mvppro.contract.RecommendContract;
import com.shell.mvppro.model.RecommendModel;
import com.shell.mvppro.uitls.ToastUtils;

/**
 * @author ShellRay
 * Created  on 2020/3/19.
 * @description
 */
public class RecommendPresenter extends BasePresenter<RecommendContract.RecommendView> implements RecommendContract.RecommendPresenter {

    @InjectModel
    RecommendModel model;

    @Override
    public void reqDataP(Context context, int index) {
        model.reqDataM(context, index, new NetworkCallback<RecListResponse<BiliAppIndex>>() {
            @Override
            public void onSuccess(RecListResponse<BiliAppIndex> response) {
                getView().updateStateView(response);
            }

            @Override
            public void failure(String msg) {

            }
        });

    }
}
