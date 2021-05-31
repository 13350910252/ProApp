package com.yinp.proapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.yinp.proapp.constant.SpConstants.CODE;
import static com.yinp.proapp.constant.SpConstants.SP_NAME;

public class SpUtils {
    /**
     * 保存boolean常量
     */
    public static void saveBoolean(boolean isFirst, Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, CODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, isFirst);
        editor.apply();
    }

    /**
     * 获取boolean常量
     */
    public static boolean getBoolean(Context context, String key, Boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, CODE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存String常量
     */
    public static void saveString(String value, Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, CODE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 获取String常量
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, CODE);
        return sp.getString(key, defValue);
    }

    /**
     * 保存Int常量
     */
    public static void saveInt(int value, Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, CODE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    /**
     * 获取Int常量
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, CODE);
        return sp.getInt(key, defValue);
    }
}
