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

import java.util.ArrayList;
import java.util.List;


/**
 * 商品详情总页
 */

public class PurchaseDetailsFragment extends BaseFragment {
    private ConvenientBanner convenientBanner;
    private TabLayout tab_layout_detail;
    private ViewPager viewpager_detail;

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置actionBar标题
        setTitle("详情");
        View view = inflater.inflate(R.layout.fragment_purchase_detail, container, false);
        findView(view);

        //此方法在做fragment界面跳转时必须要调用的方法，用于显示数据请求成功后的界面展示
        showPager(ContentPager.PAGER_SUCCESS);
        return view;
    }

    private void findView(View view) {
        tab_layout_detail = (TabLayout) view.findViewById(R.id.tab_layout_detail);
        viewpager_detail = (ViewPager) view.findViewById(R.id.viewpager_detail);
        tab_layout_detail.setupWithViewPager(viewpager_detail);
        DetailPagerAdapter adapter = new DetailPagerAdapter(cfm);
     /*   PagerListener listener = new PagerListener();
        viewpager_detail.setOnPageChangeListener(listener);*/
        viewpager_detail.setAdapter(adapter);

    }
//    final class PagerListener implements ViewPager.OnPageChangeListener{
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    }

    final class DetailPagerAdapter extends FragmentPagerAdapter {
        final String[] titles ={"商品","详情","评价"};
        public List<BaseFragment> fragments;

        public DetailPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            fragments.add(new PurchaseDetailsGoodsFragment());
            fragments.add(new PurchaseDetailsInfoFragment());
            fragments.add(new PurchaseDetailsAppraiseFragment());
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment =fragments.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}

