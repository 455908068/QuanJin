package com.xqkj.quanjin.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xqkj.quanjin.R;
import com.xqkj.quanjin.helper.CommonHelper;


/**
 * 项目中维护页面状态的基类
 */
public final class ContentPager extends FrameLayout {
    //public final class ContentPager extends NestedScrollView {
    private static final int STATE_SHIFT = 30;
    public static final int PAGER_SUCCESS = 0 << STATE_SHIFT;
    public static final int PAGER_EMPTY = 1 << STATE_SHIFT;
    public static final int PAGER_ERROR = 2 << STATE_SHIFT;
    public static final int PAGER_LOADING = 3 << STATE_SHIFT;

    private final Handler handler;
    private ViewGroup loadingPager;
    private ViewGroup errorPager;
    private ViewGroup emptyPager;
    private ViewGroup successPager;

    public ContentPager(Context context) {
        this(context, null);
    }

    public ContentPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handler = new Handler(Looper.getMainLooper());
        loadingPager = (ViewGroup) inflate(getContext(), R.layout.pager_loading, null);
        errorPager = (ViewGroup) inflate(getContext(), R.layout.pager_error, null);
        emptyPager = (ViewGroup) inflate(getContext(), R.layout.pager_empty, null);
        successPager = (ViewGroup) inflate(getContext(), R.layout.frame_layout, null);
        showPager(PAGER_LOADING);
    }

    /**
     * 根据当前的状态来展示不同的页面
     */
    public void showPager(final int state) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                removeAllViews();
                switch (state) {
                    case PAGER_LOADING:
                        addView(loadingPager);
                        break;
                    case PAGER_ERROR:
                        addView(errorPager);
                        break;
                    case PAGER_EMPTY:
                        addView(emptyPager);
                        break;
                    case PAGER_SUCCESS:
                        addView(successPager);
                        break;
                }
            }
        });
    }

    public void addSuccessPagerView(View child) {
        if (null == child)
            return;
        CommonHelper.removeSelf(child);
        if (successPager.getChildCount() > 0)
            successPager.removeAllViews();
        successPager.addView(child);
    }

    public boolean isShowingSuccessPager() {
        return null != successPager.getParent();
    }

    public void setOnClickErrorPagerListener(OnClickListener l) {
        if (null != l) {
            emptyPager.setOnClickListener(l);
        }
    }

    public void setOnClickEmptyPagerListener(OnClickListener l) {
        if (null != l) {
            emptyPager.setOnClickListener(l);
        }
    }
}
