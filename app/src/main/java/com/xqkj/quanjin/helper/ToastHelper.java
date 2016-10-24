package com.xqkj.quanjin.helper;

import android.content.Context;
import android.widget.Toast;

import com.xqkj.quanjin.base.App;


/**
 * Toash类的便捷封装工具类
 */
public class ToastHelper {
    private static Toast toast;

    private ToastHelper() {
    }

    public static void show(Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        } else {
            toast.cancel();
        }
        toast.setText(text);
        toast.show();
    }

    public static void show(final CharSequence text) {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(App.context, null, Toast.LENGTH_SHORT);
                }
                toast.setText(text);
                toast.show();
            }
        });
    }
}
