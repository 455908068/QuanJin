package com.xqkj.quanjin.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.xqkj.quanjin.R;
import com.xqkj.quanjin.base.BasicFragment;
import com.xqkj.quanjin.helper.ApiHelper;
import com.xqkj.quanjin.helper.LogHelper;
import com.xqkj.quanjin.helper.NetworkImageHolderView;
import com.xqkj.quanjin.helper.OkHttpHelper;
import com.xqkj.quanjin.helper.ToastHelper;
import com.xqkj.quanjin.main.me.MeFragment;
import com.xqkj.quanjin.main.purchase.PurchaseDetailsFragment;
import com.xqkj.quanjin.widget.ContentPager;
import com.xqkj.quanjin.widget.GridItemDecoration;
import com.xqkj.quanjin.widget.StaggeredItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 首页
 */

public class HomeFragment extends BasicFragment {
    private final static String TAG = HomeFragment.class.getSimpleName();
    private ConvenientBanner convenientBanner;
    private HashMap<String, String> map;

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findView(view);
        System.out.print("haha");

        showPager(ContentPager.PAGER_SUCCESS);

        return view;
    }

    //View的初始化以及recycleView等适配器的设定
    private void findView(View view) {
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenient_banner);

        //ToDo 网络请求未做，需要实现后加载图片实现轮播效果
        // banner();

        //热销品牌商品RecycleView的设定
        RecyclerView recycler_view_hot_logo = (RecyclerView) view.findViewById(R.id.recycler_view_hot_logo);
        recycler_view_hot_logo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        HotLogoAdapter hotLogoAdapter = new HotLogoAdapter();
        recycler_view_hot_logo.setAdapter(hotLogoAdapter);

        //优质单品RecycleView的设定
        RecyclerView recycler_view_tail = (RecyclerView) view.findViewById(R.id.recycler_view_tail);
        recycler_view_tail.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        TailAdapter tailAdapter = new TailAdapter();
        recycler_view_tail.setAdapter(tailAdapter);
        recycler_view_tail.addItemDecoration(new GridItemDecoration(getContext()));
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
    //优质单品适配器
    final class TailAdapter extends RecyclerView.Adapter<TailAdapter.TailHolder> {
        private List<String> items;

        public TailAdapter() {
            items = new ArrayList<>();
        }

        //创建ViewHolder对象
        @Override
        public TailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater(null).inflate(R.layout.item_fragment_home_tail, null);
            return new TailHolder(itemView);
        }

        //绑定ViewHolder
        @Override
        public void onBindViewHolder(TailHolder holder, int position) {
//            String s = items.get(position);

        }

        //获取条目个数
        @Override
        public int getItemCount() {
//            return items.size();
            return 30;
        }

        //优质单品ViewHolder
        final class TailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TailHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
            }

            //条目的点击事件要在ViewHolder类中实现
            @Override
            public void onClick(View v) {
                ToastHelper.show("onClick="+getAdapterPosition());
                //跳转到购物详情页面
                startStandardActivity(PurchaseDetailsFragment.class);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 热销品牌适配器
     */
    final class HotLogoAdapter extends RecyclerView.Adapter<HotLogoAdapter.HotLogoHolder> {
        private List<String> items;

        public HotLogoAdapter() {
            items = new ArrayList<>();
        }
        //创建ViewHolder对象方法，关联条目
        @Override
        public HotLogoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater(null).inflate(R.layout.item_fragment_home, null);
            return new HotLogoHolder(itemView);
        }
        //绑定ViewHolder
        @Override
        public void onBindViewHolder(HotLogoHolder holder, int position) {
//            String uri = items.get(position);
            holder.iv_logo.setImageResource(R.drawable.ic_launcher);
        }
        //获取条目个数
        @Override
        public int getItemCount() {
//            return items.size();
            return 10;
        }

        //热销品牌ViewHolder，条目的点击事件在ViewHolder类中实现
        final class HotLogoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final ImageView iv_logo;

            public HotLogoHolder(View itemView) {
                super(itemView);
                iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);

                itemView.setOnClickListener(this);
            }
            //条目的点击事件方法
            @Override
            public void onClick(View v) {
                ToastHelper.show("position=" + getAdapterPosition());
                //跳转到购物详情页面
                startStandardActivity(PurchaseDetailsFragment.class);
            }
        }
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
        HashMap<String, String> map =  new HashMap<>();
        map.put("userid",userid);
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
                final BannerBean info = new Gson().fromJson(body, BannerBean.class);
                LogHelper.e(TAG, info.toString());
                switch (info.returnCode) {
                    case "success":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> imageUrls = new ArrayList<>();
                                for (int i = 0; i < info.androidImages.size(); i++) {
                                    BannerBean.Banner banner = info.androidImages.get(i);
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
        public List<Banner> androidImages;
        public List<Banner> iosImages;

        final class Banner {
            public String imageUrl;
            public String url;
            public String hint;
            public String id;
        }
    }
}
