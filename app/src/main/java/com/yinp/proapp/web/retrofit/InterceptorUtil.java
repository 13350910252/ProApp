package com.yinp.proapp.web.retrofit;

import android.content.Context;
import android.util.Log;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/*
 *工具拦截器
 *
 * */
public class InterceptorUtil {
    public static final String TAG = "------";

    public static HttpLoggingInterceptor LogInterceptor() {     //日志拦截器,用于打印返回请求的结果
        return new HttpLoggingInterceptor(message -> {
            Log.w(TAG, "netLog:" + message);
//            if (message.contains(context.getString(R.string.code_fail))) {
//                ToastUtils.getInstance(AppApplication.getInstance()).showToast("您的账号已在其他设备上登录，请重新登录！");
////                OftenUtils userUtils = new OftenUtils();
////                userUtils.loginOut(context);
//                context.startActivity(new Intent(context, LoginActivity.class));
//                AppManager.getAppManager().finishActivity();
//            }

        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

//    public static Interceptor HeaderInterceptor(Context context) {//头部添加请求头信息
//        return chain -> {
//            Request request = chain.request().newBuilder().
//                    addHeader("Content-Type", "application/json;charSet=UTF-8")
//                    .addHeader("Connection", "close")
//                    .addHeader("access_token", SpUtils.getString(context, TOKEN, ""))
////                    .addHeader("companyId", SpUtils.getString(context,COMPANY_ID,"2"))
//                    .addHeader("companyId", SpUtils.getString(context, COMPANY_ID, ""))
//                    .build();
//            Log.e("company", SpUtils.getString(context, COMPANY_ID, ""));
//            return chain.proceed(request);
//        };
//    }
}