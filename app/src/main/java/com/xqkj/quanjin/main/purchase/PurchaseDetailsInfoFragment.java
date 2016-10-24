package com.xqkj.quanjin.main.purchase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.xqkj.quanjin.R;
import com.xqkj.quanjin.base.BasicFragment;
import com.xqkj.quanjin.helper.ApiHelper;
import com.xqkj.quanjin.helper.LogHelper;
import com.xqkj.quanjin.helper.NetworkImageHolderView;
import com.xqkj.quanjin.helper.OkHttpHelper;
import com.xqkj.quanjin.widget.ContentPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 购物车Fragment用于实现购物车相关功能
 */

public class PurchaseDetailsInfoFragment extends BasicFragment {
    private final static String TAG = PurchaseDetailsInfoFragment.class.getSimpleName();
    private ConvenientBanner convenientBanner;
    private HashMap<String, String> map;


    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_detail_info, container, false);

        findView(view);

        showPager(ContentPager.PAGER_SUCCESS);

        return view;
    }

    //View的初始化以及recycleView等适配器的设定
    private void findView(View view) {
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenient_banner);
        banner();


    }

    //自动轮播功能的设定
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //开始自动翻页
        if (!convenientBanner.isTurning()) {
            convenientBanner.startTurning(5000);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //停止自动翻页
        if (convenientBanner.isTurning()) {
            convenientBanner.stopTurning();
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //以下是轮播请求服务
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 图片轮播
     */
    public void banner() {
        String userid = "4028d09457b2e3d90157b3376d840012";
        String url = ApiHelper.api(ApiHelper.PATH_BANNER);
        HashMap<String, String> map = new HashMap<>();
        map.put("userid", userid);
        String content = new Gson().toJson(map);
        //后台数据的请求
        OkHttpHelper.enqueue(content, url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    return;
                }
                String body = response.body().string();
                final PurchaseDetailsInfoFragment.BannerBean info = new Gson().fromJson(body, PurchaseDetailsInfoFragment.BannerBean.class);
                LogHelper.e(TAG, info.toString());
                switch (info.returnCode) {
                    case "success":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> imageUrls = new ArrayList<>();
                                for (int i = 0; i < info.androidImages.size(); i++) {
                                    PurchaseDetailsInfoFragment.BannerBean.Banner banner = info.androidImages.get(i);
                                    imageUrls.add(banner.imageUrl);
                                }
                                convenientBanner.setPages(new CBViewHolderCreator() {
                                    @Override
                                    public Object createHolder() {
                                        return new NetworkImageHolderView();
                                    }
                                }, imageUrls).setPageIndicator(new int[]{R.drawable.shape_banner, R.drawable.shape_banner_selected});
                                convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                            }
                        });
                        break;
                    case "fail":
                        break;
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    final class BannerParameters {
        public String userid;

        public BannerParameters(String userid) {
            this.userid = userid;
        }
    }

    final class BannerBean {
        public String message;
        public String returnCode;
        public List<PurchaseDetailsInfoFragment.BannerBean.Banner> androidImages;
        public List<PurchaseDetailsInfoFragment.BannerBean.Banner> iosImages;

        final class Banner {
            public String imageUrl;
            public String url;
            public String hint;
            public String id;
        }
    }
}

