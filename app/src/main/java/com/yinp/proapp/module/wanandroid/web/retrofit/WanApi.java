package com.yinp.proapp.module.wanandroid.web.retrofit;

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
     * @return
     */
    @GET("article/top/json")
    Observable<WanData> getStickList();

    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    Observable<WanData> login(@Query("username") String username, @Query("password") String password);

    /**
     * 获取自己得积分
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<WanData> getIntegral();
}
