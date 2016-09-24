package com.example.aidong.ui.activity.discover;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.TabFragmentAdapter;
import com.example.aidong.ui.fragment.discover.VenuesCoachFragment;
import com.example.aidong.ui.fragment.discover.VenuesCourseFragment;
import com.example.aidong.ui.fragment.discover.VenuesDetailFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 场馆详情
 * Created by song on 2016/9/21.
 */
public class VenuesDetailActivity extends BaseActivity{

    private ImageView ivBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_detail);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        ArrayList<Fragment> fragments = new ArrayList<>();
        VenuesDetailFragment detail = new VenuesDetailFragment();
        VenuesCoachFragment coach = new VenuesCoachFragment();
        VenuesCourseFragment course = new VenuesCourseFragment();

        fragments.add(detail);
        fragments.add(coach);
        fragments.add(course);

        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.venuesTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
