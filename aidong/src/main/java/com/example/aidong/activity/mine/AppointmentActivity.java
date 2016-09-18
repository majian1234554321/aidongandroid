package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.TabFragmentAdapter;
import com.example.aidong.fragment.mine.AppointmentFragment;
import com.leyuan.support.widget.customview.SimpleTitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的预约
 * Created by song on 2016/8/31.
 */
public class AppointmentActivity extends BaseActivity {
    private SimpleTitleBar titleBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        ArrayList<Fragment> fragments = new ArrayList<>();
        AppointmentFragment all = new AppointmentFragment();
        AppointmentFragment joined = new AppointmentFragment();
        AppointmentFragment unJoined = new AppointmentFragment();

        all.setArguments(AppointmentFragment.ALL);
        joined.setArguments(AppointmentFragment.ALL);
        unJoined.setArguments(AppointmentFragment.ALL);

        fragments.add(all);
        fragments.add(joined);
        fragments.add(unJoined);

        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.appointmentTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        viewPager.setOffscreenPageLimit(2);
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
