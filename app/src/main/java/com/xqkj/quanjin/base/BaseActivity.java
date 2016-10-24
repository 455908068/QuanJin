package com.xqkj.quanjin.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xqkj.quanjin.R;
import com.xqkj.quanjin.helper.CommonHelper;

import java.util.LinkedList;
import java.util.List;


/**
 * 基类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected FragmentManager fm;
    protected Toolbar toolbar;
    protected TextView tv_left;
    protected TextView tv_center;
    protected TextView tv_right;
    protected TabLayout tabLayout;
    protected FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            fm = getSupportFragmentManager();
            requestPermission(); //请求权限
            CacheHelper.addActivity(this); //加入缓存列表
            setContentView(R.layout.coordinator_layout);
            findViewById(null);
            replaceFragment(null);
        }
    }

    private void replaceFragment(@Nullable Bundle savedInstanceState) {
        Fragment fragment = instantiateFragment(savedInstanceState);
        if (fragment != null) {
            fm.beginTransaction().replace(R.id.nested_scroll_view, fragment).commit();
        }
    }

    /**
     * 添加fragment的通用函数，可以不做任何实现
     */
    protected Fragment instantiateFragment(@Nullable Bundle savedInstanceState) {
        return null;
    }

    protected void findViewById(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_left = (TextView) toolbar.findViewById(R.id.tv_left);
        tv_center = (TextView) toolbar.findViewById(R.id.tv_center);
        tv_right = (TextView) toolbar.findViewById(R.id.tv_right);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        tabLayout.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        tv_left.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_left) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        if (CommonHelper.isFastDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_left:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.tv_center:
                break;
            case R.id.tv_right:
                break;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Activity缓存工具类
     */
    public final static class CacheHelper {
        public static final List<AppCompatActivity> list = new LinkedList<>();

        /**
         * 添加到Activity容器中
         */
        public static void addActivity(AppCompatActivity activity) {
            if (!list.contains(activity)) {
                list.add(activity);
            }
        }

        /**
         * 结束所有Activity
         */
        public static void finishActivity() {
            for (AppCompatActivity activity : list) {
                activity.finish();
            }
        }
    }
}
