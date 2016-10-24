package com.xqkj.quanjin.helper;


import android.util.Log;

public class LogHelper {
    public static void setDebug(boolean debug) {
        LogHelper.debug = debug;
    }

    private static boolean debug = true;

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);

        }
    }

    public static void e(Object tag, String msg) {
        if (debug) {
            Log.e(tag.getClass().getSimpleName(), msg);
        }
    }
}
