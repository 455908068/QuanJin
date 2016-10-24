package com.xqkj.quanjin.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import cn.jpush.android.api.JPushInterface;

/**
 * 办单系统Application
 */

public class App extends Application {
    //项目中公用的上下文对象
    public static Context context;
    public static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        handler = new Handler();
        //极光初始化
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
    }
}
