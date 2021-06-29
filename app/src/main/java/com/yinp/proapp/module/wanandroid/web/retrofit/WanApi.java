package com.yinp.proapp.module.wanandroid.web.retrofit;

import com.yinp.proapp.module.wanandroid.bean.CollectionListBean;
import com.yinp.proapp.module.wanandroid.bean.RankListBean;
import com.yinp.proapp.module.wanandroid.bean.WanSysListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanApi {
    /**
     * 获取顶部banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<WanData> getBannerList();

    /**
     * https://www.wanandroid.com/article/list/0/json
     *
     * @param size
     * @return
     */
    @GET("article/list/{size}/json")
    Observable<WanData> getHomArticleList(@Path("size") int size);

    /**
     * 获取置顶文章
     *
     * @return
     */
    @GET("article/top/json")
    Observable<WanData> getStickList();

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    Observable<WanData> login(@Query("username") String username, @Query("password") String password);

    /**
     * 获取自己得积分
     *
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<WanData> getIntegral();

    /**
     * 获取积分排行榜
     *
     * @return
     */
    @GET("coin/rank/{page}/json")
    Observable<WanData2<RankListBean>> getRankList(@Path("page") int page);

    /**
     * 获取体系列表
     *
     * @return
     */
    @GET("tree/json")
    Observable<WanData2<List<WanSysListBean>>> getSystemList();

    /**
     * 获取收藏列表
     * https://www.wanandroid.com/lg/collect/list/0/json
     *
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<WanData2<CollectionListBean>> getCollectList(@Path("page") int page);
}
