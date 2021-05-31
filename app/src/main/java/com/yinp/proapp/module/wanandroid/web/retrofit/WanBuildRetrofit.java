package com.yinp.proapp.module.wanandroid.web.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yinp.proapp.web.retrofit.AddCookiesInterceptor;
import com.yinp.proapp.web.retrofit.BuildRetrofit;
import com.yinp.proapp.web.retrofit.InterceptorUtil;
import com.yinp.proapp.web.retrofit.SaveCookiesInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.JavaNetCookieJar;
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
        client = new OkHttpClient.Builder()
                //添加log拦截器
//                .addInterceptor(interceptor)
                .addInterceptor(InterceptorUtil.LogInterceptor())
                .addInterceptor(new AddCookiesInterceptor(mContext))
                .addInterceptor(new SaveCookiesInterceptor(mContext))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
//                .cookieJar(getCookieJar())
//                .cookieJar(new CookieJar() {
//                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
//
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        cookieStore.put(url, cookies);
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        List<Cookie> cookies = cookieStore.get(url);
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
//                    }
//                })
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
                    mContext =context;
                    buildRetrofit = new WanBuildRetrofit();
                }
            }
        }
        return buildRetrofit;
    }

    public static WanBuildRetrofit getInstance(Context context,String baseUrl) {
        if (buildRetrofit == null) {
            synchronized (Object.class) {
                if (buildRetrofit == null) {
                    mContext =context;
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

    private CookieJar getCookieJar() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
        return cookieJar;
    }
}
