package com.xqkj.quanjin.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.xqkj.quanjin.R;
import com.xqkj.quanjin.base.BaseActivity;
import com.xqkj.quanjin.helper.ToastHelper;
import com.xqkj.quanjin.main.category.CategoryFragment;
import com.xqkj.quanjin.main.home.HomeFragment;
import com.xqkj.quanjin.main.me.MeFragment;
import com.xqkj.quanjin.main.order.OrderFragment;
import com.xqkj.quanjin.main.purchase.PurchaseFragment;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private PurchaseFragment purchaseFragment;
   // private OrderFragment orderFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();;
        setContentView(R.layout.activity_main);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.check(R.id.rb_home);
        initFragments();
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initFragments() {
        homeFragment = (HomeFragment) Fragment.instantiate(this, HomeFragment.class.getName());
        categoryFragment = (CategoryFragment) Fragment.instantiate(this, CategoryFragment.class.getName());
        purchaseFragment = (PurchaseFragment) Fragment.instantiate(this, PurchaseFragment.class.getName());
        //orderFragment = (OrderFragment) Fragment.instantiate(this, OrderFragment.class.getName());
        meFragment = (MeFragment) Fragment.instantiate(this, MeFragment.class.getName());

        fm.
                beginTransaction().
                add(R.id.fragment_host, homeFragment, "HomeFragment").
                add(R.id.fragment_host, categoryFragment, "CategoryFragment").
                add(R.id.fragment_host, purchaseFragment, "PurchaseFragment").
               // add(R.id.fragment_host, orderFragment, "OrderFragment").
                add(R.id.fragment_host, meFragment, "MeFragment").
                show(homeFragment).
                hide(categoryFragment).
                hide(purchaseFragment).
              //  hide(orderFragment).
                hide(meFragment).
                commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        homeFragment.onTabSelected(null);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                fm.
                        beginTransaction().
                        show(homeFragment).
                        hide(categoryFragment).
                        hide(purchaseFragment).
                       // hide(orderFragment).
                        hide(meFragment).
                        commit();
                homeFragment.onTabSelected(null);
                break;
            case R.id.rb_category:
                fm.
                        beginTransaction().
                        hide(homeFragment).
                        show(categoryFragment).
                        hide(purchaseFragment).
                      //  hide(orderFragment).
                        hide(meFragment).
                        commit();
                categoryFragment.onTabSelected(null);
                break;
            case R.id.rb_purchase:
                fm.
                        beginTransaction().
                        hide(homeFragment).
                        hide(categoryFragment).
                        show(purchaseFragment).
                       // hide(orderFragment).
                        hide(meFragment).
                        commit();
                purchaseFragment.onTabSelected(null);
                break;
            /*case R.id.rb_order:
                fm.
                        beginTransaction().
                        hide(homeFragment).
                        hide(categoryFragment).
                        hide(purchaseFragment).
                        show(orderFragment).
                        hide(meFragment).
                        commit();
                orderFragment.onTabSelected(null);
                break;*/
            case R.id.rb_me:
                fm.
                        beginTransaction().
                        hide(homeFragment).
                        hide(categoryFragment).
                        hide(purchaseFragment).
                       // hide(orderFragment).
                        show(meFragment).
                        commit();
                meFragment.onTabSelected(null);
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private int height;

/*    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (height == 0) {
            height = bottom_navigation_bar.getHeight();
            nestedScrollView.setPadding(0, 0, 0, height);
        }
    }*/

    private long exitTime;


    /**
     * 物理按键点击的时候调用此方法
     * 如果大于设定的时间间隔（比如2秒），则提示再按一次返回键退出，否则直接退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
/*        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        }*/
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2 * 1000) {
                ToastHelper.show("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
//                System.exit(0);
//                finish();
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
