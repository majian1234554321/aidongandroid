package com.leyuan.aidong.ui.mine.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.CouponFragment;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponActivity extends BaseActivity implements SmartTabLayout.TabProvider{
    private List<View> allTabView = new ArrayList<>();
    private SimpleTitleBar titleBar;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        tabLayout = (SmartTabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        FragmentPagerItems pages = new FragmentPagerItems(this);
        CouponFragment valid = new CouponFragment();
        CouponFragment used = new CouponFragment();
        CouponFragment expired = new CouponFragment();
        pages.add(FragmentPagerItem.of(null, valid.getClass(),
                new Bundler().putString("type",CouponFragment.VALID).get()));
        pages.add(FragmentPagerItem.of(null, used.getClass(),
                new Bundler().putString("type",CouponFragment.USED).get()));
        pages.add(FragmentPagerItem.of(null, expired.getClass(),
                new Bundler().putString("type",CouponFragment.EXPIRED).get()));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),pages);

        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD :Typeface.DEFAULT);
                }
            }
        });

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_order_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.couponTab);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }
}
