package com.yinp.tools.utils;

import android.content.Context;

public class ToolsUtils {
    /**
     * 获取屏幕高度的px
     *
     * @return
     */
    public static float getHeightPixels(Context context) {

        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度的px
     *
     * @return
     */
    public static float getWidthPixels(Context context) {

        return context.getResources().getDisplayMetrics().widthPixels;
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
}
