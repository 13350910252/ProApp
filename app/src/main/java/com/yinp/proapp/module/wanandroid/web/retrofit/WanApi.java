package com.yinp.proapp.module.wanandroid.web.retrofit;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
     * @param size
     * @return
     */
    @GET("article/list/{size}/json")
    Observable<WanData> getHomArticleList(@Path("size") int size);

    @GET("article/top/json")
    Observable<WanData> getStickList();

    @POST("user/login")
    Observable<WanData> login(@Body RequestBody rb);
}
