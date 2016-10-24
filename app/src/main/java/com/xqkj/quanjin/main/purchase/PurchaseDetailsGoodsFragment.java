package com.xqkj.quanjin.main.purchase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.xqkj.quanjin.R;
import com.xqkj.quanjin.base.BaseFragment;
import com.xqkj.quanjin.widget.ContentPager;


/**
 * 商品页
 */

public class PurchaseDetailsGoodsFragment extends BaseFragment {
    private ConvenientBanner convenientBanner;
    private TabLayout tab_layout_detail;
    private ViewPager viewpager_detail;

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置actionBar标题
        setTitle("详情");
        View view = inflater.inflate(R.layout.fragment_purchase_detail_child, container, false);

        //此方法在做fragment界面跳转时必须要调用的方法，用于显示数据请求成功后的界面展示
        showPager(ContentPager.PAGER_SUCCESS);
        return view;
    }




}

