package com.xqkj.quanjin.main.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xqkj.quanjin.R;
import com.xqkj.quanjin.base.BasicFragment;
import com.xqkj.quanjin.widget.ContentPager;


/**
 * 托运单
 */

public class OrderFragment extends BasicFragment {
    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tyd, container, false);
        showPager(ContentPager.PAGER_SUCCESS);
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
