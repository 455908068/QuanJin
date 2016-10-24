package com.xqkj.quanjin.helper;

import android.view.View;
import android.view.ViewGroup;

/**
 * 通用工具类
 */
public class CommonHelper {
    private static long lastClickTime;

    /**
     * 解决Android中多次点击启动多个相同界面的问题
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= 1000;
    }

    /**
     * 如果page已经有一个父类，须从父类中移除
     */
    public static void removeSelf(View child) {
        if (null == child)
            return;
        if (child.getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) child.getParent();
            if (null != parent)
                parent.removeView(child);
        }
    }
}
