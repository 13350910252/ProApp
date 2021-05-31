package com.yinp.proapp.utils;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

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
}
