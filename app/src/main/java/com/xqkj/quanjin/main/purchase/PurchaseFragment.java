package com.xqkj.quanjin.main.purchase;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xqkj.quanjin.R;
import com.xqkj.quanjin.base.BasicFragment;
import com.xqkj.quanjin.widget.ContentPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 购物车Fragment用于实现购物车相关功能
 */

public class PurchaseFragment extends BasicFragment {

    private CheckBox cb_all;
    private View btn_pay;
    private PayAdapter adapter;

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);

        //RecycleView以及控件的初始化
        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        cb_all = (CheckBox) view.findViewById(R.id.cb_all);
        btn_pay = view.findViewById(R.id.btn_pay);
        //为RecycleView设置布局管理器
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PayAdapter();
        //为RecycleView设置适配器
        recycler_view.setAdapter(adapter);
        //为全选按钮设置监听器
        CheckBoxListener listener = new CheckBoxListener();
        cb_all.setOnCheckedChangeListener(listener);
        //此方法手动调用，因为默认加载请求数据不成功界面
        showPager(ContentPager.PAGER_SUCCESS);

        return view;
    }


    /**
     * CheckBox的监听器，用来实现全选反选功能
     */
    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        //当Checkbox状态发生改变的时候调用此方法
        @Override
        public void onCheckedChanged(CompoundButton buttonView,/*复合控件*/ boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_all:
                    seclectAll(isChecked);
                    break;
//                反选逻辑（预留）
//                case R.id.cb_reverse:
//                    selectReverse();
//                    break;
            }

        }

    }

    //CheckBox反选方法,预留方法
    private void selectReverse() {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            boolean temp = adapter.items.get(i).isChecked;
            adapter.items.get(i).isChecked = !temp;
        }
        adapter.notifyDataSetChanged();
    }

    //CheckBox全选方法
    private void seclectAll(boolean isChecked) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            adapter.items.get(i).isChecked = isChecked;
        }
        adapter.notifyDataSetChanged();
    }

    //RecycleView条目的Bean类
    final class Item {
        public String item;
        public boolean isChecked;

        public Item(String item, boolean isChecked) {
            this.item = item;
            this.isChecked = isChecked;
        }
    }

    /**
     * RecycleView的适配器，用于为recycleView适配数据
     */
    final class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayHolder> {
        private final List<Item> items;

        public PayAdapter() {
            items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Item item = new Item("库存" + i + "件", false);
                items.add(item);
            }
        }

        //创建ViewHolder对象方法，关联条目
        @Override
        public PayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater(null).inflate(R.layout.item_fragment_purchase, null);
            return new PayHolder(itemView);
        }

        //绑定ViewHolder方法
        @Override
        public void onBindViewHolder(PayHolder holder, int position) {
            Item item = items.get(position);
            holder.tv_git.setText(item.item);
            holder.check_box.setChecked(item.isChecked);
        }

        //获取条目数量的方法
        @Override
        public int getItemCount() {
            return items.size();
        }


        /**
         * Holder类，继承自RecycleView.ViewHolder类
         */
        final class PayHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

            private final CheckBox check_box;
            private final TextView tv_git;

            public PayHolder(View itemView) {
                super(itemView);
                check_box = (CheckBox) itemView.findViewById(R.id.check_box);
                tv_git = (TextView) itemView.findViewById(R.id.tv_git);
                check_box.setOnCheckedChangeListener(this);
                //为条目设置点击事件监听器
                itemView.setOnClickListener(this);
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                items.get(getAdapterPosition()).isChecked = isChecked;
            }

            //条目点击事件的onClick()方法，跳转到商品详情页
            @Override
            public void onClick(View v) {
                //跳转到购物详情页面
                startStandardActivity(PurchaseDetailsFragment.class);
            }
        }

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

