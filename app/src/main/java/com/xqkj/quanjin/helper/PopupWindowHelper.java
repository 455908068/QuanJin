package com.xqkj.quanjin.helper;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by lcj on 2016/7/4.
 */
public class PopupWindowHelper {
    public static PopupWindow newPopupWindow(View contentView, Drawable background) {
//        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        PopupWindow window = new PopupWindow();
        window.setContentView(contentView);
        window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setBackgroundDrawable(background);
        return window;
    }

    public static PopupWindow showAsDropDown(PopupWindow window, View anchor) {
        window.showAsDropDown(anchor);
        return window;
    }

    public static PopupWindow showAsDropDown(PopupWindow window, View anchor, int xoff, int yoff) {
        window.showAsDropDown(anchor, xoff, yoff);
        return window;
    }

    public static PopupWindow showAtLocation(PopupWindow window, View parent, int gravity, int x, int y) {
        window.showAtLocation(parent, gravity, x, y);
        return window;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static PopupWindow showAsDropDown(PopupWindow window, View anchor, int xoff, int yoff, int gravity) {
        window.showAsDropDown(anchor, xoff, yoff, gravity);
        return window;
    }

    public static void dismiss(PopupWindow window) {
        window.dismiss();
    }
}
