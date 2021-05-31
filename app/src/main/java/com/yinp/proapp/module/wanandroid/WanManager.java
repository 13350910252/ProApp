package com.yinp.proapp.module.wanandroid;

import android.content.Context;

import com.yinp.proapp.manger.BaseManager;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanBuildRetrofit;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;

import okhttp3.RequestBody;

public class WanManager extends BaseManager {
    private Context context;

    public WanManager(Context context) {
        this.context = context;
    }

    /**
     * 获取顶部banner
     *
     * @param baseObserver
     */
    public void getBannerList(WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance().getWanApiRetrofit().getBannerList(), baseObserver);
    }

    /**
     * 获取首页文章列表
     *
     * @param size
     * @param baseObserver
     */
    public void getHomeList(int size, WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance().getWanApiRetrofit().getHomArticleList(size), baseObserver);
    }

    /**
     * 获取置顶文章
     *
     * @param baseObserver
     */
    public void getStickList(WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance().getWanApiRetrofit().getStickList(), baseObserver);
    }

    /**
     * 登录
     *
     * @param baseObserver
     */
    public void login(RequestBody rb, WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance().getWanApiRetrofit().login(rb), baseObserver);
    }
}
