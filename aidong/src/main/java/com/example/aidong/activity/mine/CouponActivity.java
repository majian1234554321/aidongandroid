package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.FoundFragmentAdapter;
import com.example.aidong.fragment.mine.AvailableCouponFragment;
import com.example.aidong.fragment.mine.ExpireCouponFragment;
import com.example.aidong.fragment.mine.UsedCouponFragment;
import com.example.aidong.interfaces.SimpleOnTabSelectedListener;
import com.leyuan.support.widget.customview.SimpleTitleBar;

import java.util.ArrayList;

/**
 * 我的优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponActivity extends BaseActivity{
    private static final int COUPON_AVAILABLE = 0;
    private static final int COUPON_USED = 1;
    private static final int COUPON_EXPIRE = 2;

    private SimpleTitleBar titleBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        ArrayList<Fragment> fragments = new ArrayList<>();
        AvailableCouponFragment available = new AvailableCouponFragment();
        UsedCouponFragment used = new UsedCouponFragment();
        ExpireCouponFragment overdue = new ExpireCouponFragment();
        fragments.add(available);
        fragments.add(used);
        fragments.add(overdue);

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.coupon_available));
        titles.add(getString(R.string.coupon_used));
        titles.add(getString(R.string.coupon_expire));

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
                if(tab.equals(tabLayout.getTabAt(COUPON_AVAILABLE))){
                    viewPager.setCurrentItem(COUPON_AVAILABLE);
                }else if(tab.equals(tabLayout.getTabAt(COUPON_USED))){
                    viewPager.setCurrentItem(COUPON_USED);
                }else {
                    viewPager.setCurrentItem(COUPON_EXPIRE);
                }
            }
        });
    }
}
