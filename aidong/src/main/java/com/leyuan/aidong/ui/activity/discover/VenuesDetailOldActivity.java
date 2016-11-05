package com.leyuan.aidong.ui.activity.discover;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.aidong.adapter.TabFragmentAdapter;
import com.leyuan.aidong.ui.fragment.discover.VenuesCoachFragment;
import com.leyuan.aidong.ui.fragment.discover.VenuesCourseFragment;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.widget.customview.ViewPagerIndicator;

import java.util.ArrayList;

/**
 * 场馆详情
 * Created by song on 2016/8/27
 */
@Deprecated
public class VenuesDetailOldActivity extends BaseActivity {

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    private ViewPager bannerViewPager;
    private ViewPagerIndicator indicator;

    private TabLayout tabLayout;
    private ViewPager tabViewPager;

    private TextView tvTitle;
    public  int id =1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_detail_old);

        initToolbar();
        initBannerViewPager();
        initTabLayout();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_tool_bar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());
        tvTitle = (TextView)findViewById(R.id.tv_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
        return true;
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
    }

    private void initBannerViewPager() {
        bannerViewPager = (ViewPager) findViewById(R.id.vp_banner);
        indicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        bannerViewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(bannerViewPager);
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabViewPager = (ViewPager) findViewById(R.id.vp_container);

        ArrayList<Fragment> fragments = new ArrayList<>();
        VenuesCoachFragment coachFragment = new VenuesCoachFragment();
        VenuesCourseFragment courseFragment = new VenuesCourseFragment();
        fragments.add(coachFragment);
        fragments.add(courseFragment);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("课程");
        titles.add("教练");

        tabViewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        tabViewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(tabViewPager);
    }

    private class MyOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            // int maxScroll = appBarLayout.getTotalScrollRange();
            int maxScroll = DensityUtil.dp2px(VenuesDetailOldActivity.this, 200);
            if ((float) Math.abs(verticalOffset) <= maxScroll) {
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                toolbar.setBackgroundColor(Color.argb((int) (percentage * 255), 0, 0, 0));
                tvTitle.setVisibility(View.GONE);
            }else{
                toolbar.setBackgroundColor(Color.parseColor("#000000"));
                tvTitle.setVisibility(View.VISIBLE);
            }


        }
    }
}
