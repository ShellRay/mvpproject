package com.shell.mvppro.contract;

import android.content.Context;

import com.shell.mvppro.basemvp.base.BaseView;
import com.shell.mvppro.basemvp.callback.NetworkCallback;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.bean.RecListResponse;

/**
 * @author ShellRay
 * Created  on 2020/3/17.
 * @description
 */
public class RecommendContract {

    public interface RecommendView extends BaseView {
        void updateStateView(RecListResponse<BiliAppIndex> bean);
    }

    public interface RecommendPresenter{
        void reqDataP(Context context, int index);
    }

    public interface RecommendModle  {
        void reqDataM(Context context, int index, NetworkCallback callback);
    }
}
