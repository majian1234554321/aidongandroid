package com.leyuan.aidong.ui.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.TabFragmentAdapter;
import com.leyuan.aidong.ui.fragment.mine.CouponFragment;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponActivity extends BaseActivity{
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
        CouponFragment valid = CouponFragment.newInstance(CouponFragment.VALID);
        CouponFragment used = CouponFragment.newInstance(CouponFragment.USED);
        CouponFragment expired = CouponFragment.newInstance(CouponFragment.EXPIRED);
        fragments.add(valid);
        fragments.add(used);
        fragments.add(expired);

        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.couponTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        titleBar.setBackListener(new SimpleTitleBar.OnBackClickListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }
}
