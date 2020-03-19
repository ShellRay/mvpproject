package com.shell.mvppro.network.apinet;


import com.shell.mvppro.network.common.IdeaApi;

public class RetrofitHelper {
    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService() {
        if (mIdeaApiService == null)
            mIdeaApiService = IdeaApi.getApiService(IdeaApiService.class, ServerConfig.BASE_URL);
        return mIdeaApiService;
    }
}
