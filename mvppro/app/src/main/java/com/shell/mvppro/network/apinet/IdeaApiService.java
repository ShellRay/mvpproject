package com.shell.mvppro.network.apinet;


import com.shell.mvppro.bean.ArticleWrapper;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.bean.RecListResponse;
import com.shell.mvppro.bean.LoginRequest;
import com.shell.mvppro.bean.LoginResponse;
import com.shell.mvppro.network.common.ResponseObserver;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 */

public interface IdeaApiService {

    @GET("article/list/0/json")
    Observable<ArticleWrapper> getArticle();

    /**
     * 登录 appId secret
     * 使用实体类作为参数
     *
     * @return
     */
    @POST("user/login")
    Observable<LoginResponse> login(@Body LoginRequest request);

    /**
     * 使用map作为参数
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login(@FieldMap Map<String, Object> map);

    @GET("/x/feed/index")
    Observable<RecListResponse<BiliAppIndex>> getIndex(@Query("appkey") String appkey,
                                                       @Query("build") String build,
                                                       @Query("idx") int idx,
                                                       @Query("login_event") int login_event,
                                                       @Query("mobi_app") String mobi_app,
                                                       @Query("network") String network,
                                                       @Query("open_event") String open_event,
                                                       @Query("platform") String platform,
                                                       @Query("pull") boolean pull,
                                                       @Query("style") int style,
                                                       @Query("ts") String ts
    );
}
