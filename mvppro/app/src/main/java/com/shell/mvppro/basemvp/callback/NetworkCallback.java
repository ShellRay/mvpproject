package com.shell.mvppro.basemvp.callback;


import com.shell.mvppro.network.common.BasicResponse;

public interface NetworkCallback<T extends BasicResponse> {

    void onSuccess(T t);
    void failure(String msg);
}
