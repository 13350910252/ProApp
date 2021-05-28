package com.yinp.proapp.web.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRetrofit {
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseRetrofitData> accountLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 获取顶部banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<BaseRetrofitData> getWanBannerList();

    //    https://www.wanandroid.com/article/list/0/json
    @GET("article/list/{size}/json")
    Observable<BaseRetrofitData> getWanHomArticleList(@Path("size") int size);
}
