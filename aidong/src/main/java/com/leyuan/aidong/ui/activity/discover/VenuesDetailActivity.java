package com.leyuan.aidong.ui.activity.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.TabFragmentAdapter;
import com.leyuan.aidong.ui.fragment.discover.VenuesCoachFragment;
import com.leyuan.aidong.ui.fragment.discover.VenuesCourseFragment;
import com.leyuan.aidong.ui.fragment.discover.VenuesDetailFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 场馆详情
 * Created by song on 2016/9/21.
 */
public class VenuesDetailActivity extends BaseActivity{
    private ImageView ivBack;
    private TextView tvAppointment;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String id;

    public static void start(Context context,String id) {
        Intent starter = new Intent(context, VenuesDetailActivity.class);
        starter.putExtra("id",id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_detail);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
        }

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        tvAppointment = (TextView) findViewById(R.id.tv_appointment);

        ArrayList<Fragment> fragments = new ArrayList<>();
        VenuesDetailFragment detail = VenuesDetailFragment.newInstance(id);
        VenuesCoachFragment coach =  VenuesCoachFragment.newInstance(id);
        VenuesCourseFragment course = VenuesCourseFragment.newInstance(id);
        fragments.add(detail);
        fragments.add(coach);
        fragments.add(course);

        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.venuesTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        tabLayout.setupWithViewPager(viewPager);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointVenuesActivity.start(VenuesDetailActivity.this,"id");
            }
        });
    }
}
