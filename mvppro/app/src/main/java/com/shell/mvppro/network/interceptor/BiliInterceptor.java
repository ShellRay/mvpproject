package com.shell.mvppro.network.interceptor;


import com.shell.mvppro.network.apinet.ApiHelper;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ShellRay
 * Created  on 2020/3/20.
 * @description
 */
public class BiliInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();
        String sign = ApiHelper.getSign(oldRequest.url());
        //添加sign参数
        HttpUrl.Builder newBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(ApiHelper.PARAM_SIGN, sign);
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(newBuilder.build())
                .build();
        return chain.proceed(newRequest);
    }
}
