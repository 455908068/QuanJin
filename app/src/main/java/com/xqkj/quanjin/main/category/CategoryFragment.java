package com.xqkj.quanjin.main.category;

import android.content.Intent;
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
 * 出货凭单主页
 */

public class CategoryFragment extends BasicFragment {
    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_odo, container, false);
        View tv_apply = view.findViewById(R.id.tv_apply);
        View tv_search = view.findViewById(R.id.tv_search);
        View layout_new = view.findViewById(R.id.layout_new);
        View layout_pending_audit = view.findViewById(R.id.layout_pending_audit);
        View layout_no_via = view.findViewById(R.id.layout_no_via);
        View layout_via = view.findViewById(R.id.layout_via);

        tv_apply.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        layout_new.setOnClickListener(this);
        layout_pending_audit.setOnClickListener(this);
        layout_no_via.setOnClickListener(this);
        layout_via.setOnClickListener(this);
//        showPager(ContentPager.PAGER_SUCCESS);

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
