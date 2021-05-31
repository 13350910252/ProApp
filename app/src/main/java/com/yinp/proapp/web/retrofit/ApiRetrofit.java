package com.yinp.proapp.web.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRetrofit {
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseRetrofitData> accountLogin(@Field("username") String username, @Field("password") String password);

}
