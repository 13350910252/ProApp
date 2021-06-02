package com.yinp.proapp.web.retrofit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : jc.lu
 * @create : 17/07/07.
 */
public class AddCookiesInterceptor implements Interceptor {
    private Context mContext;
    private String mName;

    public AddCookiesInterceptor(Context context, String name) {
        mContext = context;
        mName = name;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) mContext.getSharedPreferences(mName, mContext.MODE_PRIVATE).getStringSet("cookie", null);
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        return chain.proceed(builder.build());
    }
}