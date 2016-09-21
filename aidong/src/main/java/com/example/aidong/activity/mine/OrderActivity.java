package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.TabFragmentAdapter;
import com.example.aidong.fragment.mine.OrderFragment;
import com.leyuan.support.widget.customview.SimpleTitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单
 * Created by song on 2016/8/31.
 */
public class OrderActivity extends BaseActivity{

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
        OrderFragment all = OrderFragment.newInstance(OrderFragment.ALL);
        OrderFragment unpaid = OrderFragment.newInstance(OrderFragment.UN_PAID);
        OrderFragment selfPickUp = OrderFragment.newInstance(OrderFragment.SELF_DELIVERY);
        OrderFragment express = OrderFragment.newInstance(OrderFragment.EXPRESS_DELIVERY);

        fragments.add(all);
        fragments.add(unpaid);
        fragments.add(selfPickUp);
        fragments.add(express);

        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.orderTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        tabLayout.setupWithViewPager(viewPager);

        titleBar.setBackListener(new SimpleTitleBar.OnBackClickListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }
}
