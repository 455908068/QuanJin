package com.xqkj.quanjin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.xqkj.quanjin.base.BaseActivity;
import com.xqkj.quanjin.helper.LogHelper;
import com.xqkj.quanjin.main.me.MeFragment;
import com.xqkj.quanjin.main.purchase.PurchaseDetailsFragment;


/**
 * android:launchMode="standard"
 */
public class StandardActivity extends BaseActivity {
    private final static String TAG = StandardActivity.class.getSimpleName();

    @Override
    protected Fragment instantiateFragment(@Nullable Bundle savedInstanceState) {
        String simpleName = getIntent().getStringExtra("SimpleName");
        Fragment fragment = null;
        if (TextUtils.isEmpty(simpleName)) {
            return null;
        }
    /*    if (TextUtils.isEmpty(simpleName)) {
            fragment = Fragment.instantiate(this, GuideFragment.class.getName());
            fm.beginTransaction().replace(R.id.nested_scroll_view, fragment).commit();
            return fragment;
        }*/

        switch (simpleName) {
            //首页模块
            case "MeFragment":
                fragment = Fragment.instantiate(this, MeFragment.class.getName());
                break;
            //购物车商品详情页模块
            case "PurchaseDetailsFragment":
                fragment = Fragment.instantiate(this, PurchaseDetailsFragment.class.getName());
                break;
            default:
                LogHelper.e(TAG, "SimpleName===" + simpleName);
        }

        return fragment;
    }
}
