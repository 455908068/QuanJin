package com.xqkj.quanjin.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.xqkj.quanjin.R;
import com.xqkj.quanjin.StandardActivity;
import com.xqkj.quanjin.helper.CommonHelper;
import com.xqkj.quanjin.widget.ContentPager;

import okhttp3.Response;


/**
 * 项目中fragment基类
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Response> {
    protected final static SharedPreferences globalSP = App.context.getSharedPreferences(App.context.getPackageName(), Context.MODE_PRIVATE);
    protected static SharedPreferences currentUserSP;
    protected ActionBar actionBar;
    protected Toolbar toolbar;
    protected TextView tv_left;
    protected TextView tv_title;
    protected TextView tv_right;
    protected TabLayout tabLayout;
    protected FloatingActionButton fab;

    protected Handler handler;
    protected FragmentManager cfm;
    protected LoaderManager lm;
    protected Bundle arguments;
    protected ContentPager contentPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            if ((getActivity() instanceof BaseActivity)) {
                BaseActivity activity = (BaseActivity) getActivity();
                toolbar = activity.toolbar;
                tv_left = activity.tv_left;
                tv_title = activity.tv_center;
                tv_right = activity.tv_right;
                tabLayout = activity.tabLayout;
                fab = activity.fab;

                actionBar = activity.getSupportActionBar();
            }
            handler = new Handler();
            cfm = getChildFragmentManager();
            lm = getLoaderManager();
            lm.initLoader(0, null, this);
            arguments = getArguments();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == contentPager) {
            contentPager = (ContentPager) inflater.inflate(R.layout.content_pager, container, false);
            View child = createView(inflater, container, savedInstanceState);
            contentPager.addSuccessPagerView(child);
        }
        CommonHelper.removeSelf(contentPager);
        return contentPager;
    }

    /**
     * 子类需要覆写函数
     */
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (CommonHelper.isFastDoubleClick()) {
            return;
        }
    }

    protected final void showPager(int state) {
        contentPager.showPager(state);
    }

    /**
     * 设置标题
     */
    protected final void setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 隐藏的ActionBar,如果它目前正在显示。
     */
    protected final void hideActionbar() {
        if (actionBar.isShowing()) {
            actionBar.hide();
        }
    }

    /**
     * 显示ActionBar,如果它现在不显示。
     */
    protected final void showActionbar() {
        if (!actionBar.isShowing()) {
            actionBar.show();
        }
    }

    private DialogFragment dialog;

    public final void showDialog() {
        if (null == dialog) {
            dialog = (DialogFragment) Fragment.instantiate(getContext(), ProgressDialogFragment.class.getName());
        }
        dialog.show(getChildFragmentManager(), null);
    }

    public final void dismissDialog() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    public final Intent makeStandardIntent(Class clazz) {
        Intent intent = new Intent(getContext(), StandardActivity.class);
        intent.putExtra("SimpleName", clazz.getSimpleName());
        return intent;
    }

    /**
     * @param clazz fragment的类
     */
    public final void startStandardActivity(Class clazz) {
        Intent intent = new Intent(getContext(), StandardActivity.class);
        intent.putExtra("SimpleName", clazz.getSimpleName());
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    protected void hideStatusBar() {
        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getActivity().getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
            actionBar.hide();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    protected void hideNavigationBar() {
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    protected int getStatusBarHeight() {
        Resources resources = getActivity().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }

    protected int getNavigationBarHeight() {
        Resources resources = getActivity().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    @SuppressLint("NewApi")
    protected boolean checkDeviceHasNavigationBar() {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(getActivity()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //便捷工具类
    protected static SharedPreferences getSharedPreferences(Context context, String name) {
        if (null == currentUserSP) {
            currentUserSP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        return currentUserSP;
    }

    public void putString(String key, String value) {
        if (null == currentUserSP) {
            throw new NullPointerException("SharedPreferences is null");
        }
        currentUserSP.edit().putString(key, value).apply();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //下面的可以放到子类中去
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 客户端与加载管理器交互的回调接口。
     */
    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Response> loader, Response data) {

    }

    @Override
    public void onLoaderReset(Loader<Response> loader) {

    }

    /**
     * 网络数据加载器
     */
    protected abstract static class NetworkLoader extends AsyncTaskLoader<Response> {

        public NetworkLoader(Context context) {
            super(context);
        }

        @Override
        protected final void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public abstract Response loadInBackground();
    }
}
