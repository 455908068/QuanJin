package com.xqkj.quanjin.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.xqkj.quanjin.base.App;


import java.util.Set;


/**
 * SharedPreferences便捷工具类
 */
public class SPHelper {

    //文件名为.xml
    public static final String NAME = "QuanJIn";
    private static SharedPreferences sp;

    static {
        sp = App.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences(Context context, String name) {
        if (null == sp) {
            sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return sp.getString(key, null);
    }

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return sp.getInt(key, -1);
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return sp.getFloat(key, -1.0f);
    }

    public static void putLong(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(String key) {
        return sp.getLong(key, -1L);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(String key, @Nullable Set<String> values) {
        sp.edit().putStringSet(key, values).apply();
    }

    public static void clearAll() {
        sp.edit().clear().apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Nullable
    public static Set<String> getStringSet(String key) {
        return sp.getStringSet(key, null);
    }


}
