package com.xqkj.quanjin.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xqkj.quanjin.R;
import com.xqkj.quanjin.base.BasicFragment;
import com.xqkj.quanjin.helper.PackageHelper;


/**
 * 我的信息界面
 */

public class MyInfoFragment extends BasicFragment {


    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_myinfo, container, false);
        View layout_me = view.findViewById(R.id.layout_me);
        View layout_modify_pwd = view.findViewById(R.id.layout_modify_pwd);
        View layout_feed_back = view.findViewById(R.id.layout_feed_back);
        View layout_update = view.findViewById(R.id.layout_update);

        //TextView tv_version_name = (TextView) view.findViewById(R.id.tv_version_name);
        Button btn_logout = (Button) view.findViewById(R.id.btn_logout);

        layout_me.setOnClickListener(this);
        layout_modify_pwd.setOnClickListener(this);
        layout_feed_back.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

       /*
        当前版本号的设置
        String vn = "当前版本号V" + PackageHelper.versionName(getContext());
        tv_version_name.setText(vn);
        */

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
