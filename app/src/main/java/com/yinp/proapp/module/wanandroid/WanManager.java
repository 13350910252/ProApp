package com.yinp.proapp.module.wanandroid;

import android.content.Context;

import com.yinp.proapp.manger.BaseManager;
import com.yinp.proapp.module.wanandroid.bean.CollectionListBean;
import com.yinp.proapp.module.wanandroid.bean.NavigationListBean;
import com.yinp.proapp.module.wanandroid.bean.RankListBean;
import com.yinp.proapp.module.wanandroid.bean.WanSquareListBean;
import com.yinp.proapp.module.wanandroid.bean.WanSysListBean;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanBuildRetrofit;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver2;

import java.util.List;

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
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getBannerList(), baseObserver);
    }

    /**
     * 获取首页文章列表
     *
     * @param size
     * @param baseObserver
     */
    public void getHomeList(int size, WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getHomArticleList(size), baseObserver);
    }

    /**
     * 获取置顶文章
     *
     * @param baseObserver
     */
    public void getStickList(WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getStickList(), baseObserver);
    }

    /**
     * 登录
     *
     * @param baseObserver
     */
    public void login(String username, String password, WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().login(username, password), baseObserver);
    }
    /**
     * 获取自己积分数量
     *
     * @param baseObserver
     */
    public void getIntegral(WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getIntegral(), baseObserver);
    }
    /**
     * 获取积分排行榜
     *
     * @param baseObserver
     */
    public void getRankList(int page,WanObserver2<WanData2<RankListBean>> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getRankList(page), baseObserver);
    }
    /**
     * 获取自体系列表
     *
     * @param baseObserver
     */
    public void getSystemInfo(WanObserver2<WanData2<List<WanSysListBean>>> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getSystemList(), baseObserver);
    }

    /**
     * 获取收藏文章列表
     *
     * @param page
     * @param baseObserver
     */
    public void getCollectedList(int page, WanObserver2<WanData2<CollectionListBean>> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getCollectList(page), baseObserver);
    }

    /**
     * 退出的登录
     *
     * @param baseObserver
     */
    public void loginOut(WanObserver2<WanData2<String>> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().loginOut(), baseObserver);
    }

    /**
     * 获取收藏文章列表
     *
     * @param baseObserver
     */
    public void getNavigationList(WanObserver2<WanData2<List<NavigationListBean>>> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getNavigationList(), baseObserver);
    }
    /**
     * 获取广场列表数据
     *
     * @param baseObserver
     */
    public void getSquareList(int page,WanObserver2<WanData2<WanSquareListBean>> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance(context).getWanApiRetrofit().getSquareList(page), baseObserver);
    }
}
