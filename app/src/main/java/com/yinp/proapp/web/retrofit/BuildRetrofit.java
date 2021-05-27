package com.yinp.proapp.web.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BuildRetrofit {
    public static final String BASE_URL = "https://www.wanandroid.com";

    private static BuildRetrofit buildRetrofit;
    private Retrofit retrofit;
    private OkHttpClient client;
    private ApiRetrofit apiRetrofit;

    private String TAG = "------";

    /**
     * 请求访问quest
     * response拦截器
     */
    private Interceptor interceptor = chain -> {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.d(TAG, "----------Request Start----------------");
        Log.d(TAG, "| " + request.toString() + request.headers().toString());
        Log.d(TAG, "| Response:" + content);
        Log.d(TAG, "----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
//                    .addHeader()
//                    .removeHeader()
                .body(ResponseBody.create(mediaType, content))
                .build();
    };

    public BuildRetrofit(String baseUrl) {
        client = new OkHttpClient.Builder()
                //添加log拦截器
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        Log.d(TAG, "BuildRetrofit: " + baseUrl);
        Log.d(TAG, "----------------------------BuildRetrofit----------------------------------");
        apiRetrofit = retrofit.create(ApiRetrofit.class);
    }

    public static BuildRetrofit getInstance(String baseUrl) {
        if (buildRetrofit == null) {
            synchronized (Object.class) {
                if (buildRetrofit == null) {
                    buildRetrofit = new BuildRetrofit(baseUrl);
                }
            }
        }
        return buildRetrofit;
    }

    public ApiRetrofit getApiRetrofit() {
        return apiRetrofit;
    }
}
