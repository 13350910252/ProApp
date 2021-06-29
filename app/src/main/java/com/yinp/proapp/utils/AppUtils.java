package com.yinp.proapp.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.yinp.proapp.constant.Constant;
import com.yinp.proapp.module.wanandroid.bean.WanLoginBean;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Cookie;

public class AppUtils {
    /**
     * 改变状态栏文字颜色为白色和黑色
     *
     * @param isDark
     * @param window
     */
    public static void setStatusBarTextColor(boolean isDark, Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //6.0以上
            View decorView = window.getDecorView();
            int vis;
            if (isDark) {
                vis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//黑色
            } else {
                vis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;//白色
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    public static void setFullScreen(Window window) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }
        WindowManager.LayoutParams lp = window.getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setAttributes(lp);
    }

    public static String getText(String value) {
        return TextUtils.isEmpty(value) ? "" : value;
    }

    /**
     * 获取Decode的中文
     *
     * @param encodeName
     * @return
     */
    public static String getDecodeName(String encodeName) {
        String decodeName = "";
        try {
            decodeName = java.net.URLDecoder.decode(encodeName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeName;
    }

    /**
     * dp转px
     *
     * @return
     */
    public static float dpToPx(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return scale * value + 0.5f;
    }

    /**
     * px转dp
     *
     * @return
     */
    public static float pxToDp(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return value / scale + 0.5f;
    }

    public static boolean isLogin(Context context) {
        SharedPrefsCookiePersistor sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(context);
        List<Cookie> cookies = sharedPrefsCookiePersistor.loadAll();
        if (cookies != null && cookies.size() > 0) {
            WanLoginBean wanLoginBean = WanLoginBean.getUserInfo(context);
            if (wanLoginBean != null) {
                if (!TextUtils.isEmpty(wanLoginBean.getUsername())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }



    }

    public static String getValue(String value) {
        return getValue(value, "暂无");
    }

    public static String getValue(String value, String defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            return value;
        }
    }
}
