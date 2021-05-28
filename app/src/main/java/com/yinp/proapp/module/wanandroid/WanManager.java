package com.yinp.proapp.module.wanandroid;

import android.content.Context;

import com.yinp.proapp.manger.BaseManager;
import com.yinp.proapp.web.retrofit.BaseObserver;
import com.yinp.proapp.web.retrofit.BaseRetrofitData;
import com.yinp.proapp.web.retrofit.BuildRetrofit;

public class WanManager extends BaseManager {
    private Context context;

    public WanManager(Context context) {
        this.context = context;
    }

    /**
     * 获取顶部banner
     * @param baseObserver
     */
    public void getBannerList(BaseObserver<BaseRetrofitData> baseObserver) {
        addDisposable(BuildRetrofit.getInstance(BuildRetrofit.Wandroid_URL)
                .getApiRetrofit().getWanBannerList(), baseObserver);
    }
    public void getHomeList(int size,BaseObserver<BaseRetrofitData> baseObserver) {
        addDisposable(BuildRetrofit.getInstance(BuildRetrofit.Wandroid_URL)
                .getApiRetrofit().getWanHomArticleList(size), baseObserver);
    }
}