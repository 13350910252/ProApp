package com.yinp.proapp.web.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRetrofit {
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseRetrofitData> accountLogin(@Field("username") String username, @Field("password") String password);

    @GET("banner/json")
    Observable<BaseRetrofitData> getBannerList();

//    https://www.wanandroid.com/article/list/0/json
    @GET("article/list/0/json")
    Observable<BaseRetrofitData> homArticleList();
}
