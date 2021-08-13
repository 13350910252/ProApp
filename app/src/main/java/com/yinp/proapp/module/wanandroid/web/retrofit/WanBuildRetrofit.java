package com.yinp.proapp.module.wanandroid.web.retrofit;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.yinp.proapp.web.retrofit.BuildRetrofit;
import com.yinp.proapp.web.retrofit.InterceptorUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WanBuildRetrofit extends BuildRetrofit {
    public static final String BASE_URL = "https://www.wanandroid.com";
    public static final String Wandroid_URL = "https://www.wanandroid.com";

    private static WanBuildRetrofit buildRetrofit;
    private Retrofit retrofit;
    private OkHttpClient client;
    private WanApi apiRetrofit;
    private static Context mContext;

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

    public WanBuildRetrofit() {
        this(Wandroid_URL);
    }

    public WanBuildRetrofit(String baseUrl) {
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));

        client = new OkHttpClient.Builder()
                //添加log拦截器
//                .addInterceptor(interceptor)
                .addInterceptor(InterceptorUtil.LogInterceptor())
//                .addInterceptor(new AddCookiesInterceptor(mContext, Constant.WanAndroid.COOKIES)) //这部分
//                .addInterceptor(new ReceivedCookiesInterceptor(mContext, Constant.WanAndroid.COOKIES)) //这
                .cookieJar(cookieJar)
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
        apiRetrofit = retrofit.create(WanApi.class);
    }

    public static WanBuildRetrofit getInstance(Context context) {
        if (buildRetrofit == null) {
            synchronized (Object.class) {
                if (buildRetrofit == null) {
                    mContext = context;
                    buildRetrofit = new WanBuildRetrofit();
                }
            }
        }
        return buildRetrofit;
    }

    public static WanBuildRetrofit getInstance(Context context, String baseUrl) {
        if (buildRetrofit == null) {
            synchronized (Object.class) {
                if (buildRetrofit == null) {
                    mContext = context;
                    buildRetrofit = new WanBuildRetrofit(baseUrl);
                }
            }
        }
        return buildRetrofit;
    }

    public WanApi getWanApiRetrofit() {
        return apiRetrofit;
    }

    public static RequestBody toRequestBody(String json) {
        return RequestBody.create(json, MediaType.parse("application/json"));
    }
}
