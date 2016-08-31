package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.FoundFragmentAdapter;
import com.example.aidong.fragment.mine.AllOrderFragment;
import com.example.aidong.fragment.mine.ExpressOrderFragment;
import com.example.aidong.fragment.mine.SelfPickUpOrderFragment;
import com.example.aidong.fragment.mine.UnpaidOrderFragment;
import com.example.aidong.interfaces.SimpleOnTabSelectedListener;
import com.leyuan.support.widget.customview.SimpleTitleBar;

import java.util.ArrayList;

/**
 * 订单
 * Created by song on 2016/8/31.
 */
public class OrderActivity extends BaseActivity{
    private static  final int ORDER_ALL = 0;
    private static  final int ORDER_UNPAID = 1;
    private static  final int ORDER_SELF_PICK_UP = 2;
    private static  final int ORDER_EXPRESS = 3;

    private SimpleTitleBar titleBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        ArrayList<Fragment> fragments = new ArrayList<>();
        AllOrderFragment all = new AllOrderFragment();
        UnpaidOrderFragment unpaid = new UnpaidOrderFragment();
        SelfPickUpOrderFragment selfPickUp = new SelfPickUpOrderFragment();
        ExpressOrderFragment express = new ExpressOrderFragment();
        fragments.add(all);
        fragments.add(unpaid);
        fragments.add(selfPickUp);
        fragments.add(express);

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.order_all));
        titles.add(getString(R.string.order_unpaid));
        titles.add(getString(R.string.order_self_pick_up));
        titles.add(getString(R.string.order_express));

        viewPager.setAdapter(new FoundFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        titleBar.setBackListener(new SimpleTitleBar.OnBackClickListener() {
            @Override
            public void onBack() {
                finish();
            }
        });

        tabLayout.setOnTabSelectedListener(new SimpleOnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(tabLayout.getTabAt(ORDER_ALL))){
                    viewPager.setCurrentItem(ORDER_ALL);
                }else if(tab.equals(tabLayout.getTabAt(ORDER_UNPAID))){
                    viewPager.setCurrentItem(ORDER_UNPAID);
                }else if(tab.equals(ORDER_SELF_PICK_UP)){
                    viewPager.setCurrentItem(ORDER_SELF_PICK_UP);
                }else {
                    viewPager.setCurrentItem(ORDER_EXPRESS);
                }
            }
        });
    }
}
