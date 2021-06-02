package com.yinp.proapp.web.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author : jc.lu
 * @create : 17/07/07.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context mContext;
    private String mName;

    public ReceivedCookiesInterceptor(Context context, String name) {
        mContext = context;
        mName = name;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferences.Editor config = mContext.getSharedPreferences(mName, mContext.MODE_PRIVATE)
                    .edit();
            config.putStringSet("cookie", cookies);
            config.commit();
        }

        return originalResponse;
    }
}