package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.TabFragmentAdapter;
import com.example.aidong.fragment.mine.AppointmentFragment;
import com.example.aidong.interfaces.SimpleOnTabSelectedListener;
import com.leyuan.support.widget.customview.SimpleTitleBar;

import java.util.ArrayList;

/**
 * 我的预约
 * Created by song on 2016/8/31.
 */
public class AppointmentActivity extends BaseActivity {
    private static final int APPOINTMENT_ALL = 0;
    private static final int APPOINTMENT_JOINED = 1;
    private static final int APPOINTMENT_UN_JOINED = 2;

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

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.appointment_all));
        titles.add(getString(R.string.appointment_joined));
        titles.add(getString(R.string.appointment_un_joined));

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

        tabLayout.setOnTabSelectedListener(new SimpleOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.equals(tabLayout.getTabAt(APPOINTMENT_ALL))) {
                    viewPager.setCurrentItem(APPOINTMENT_ALL);
                } else if (tab.equals(tabLayout.getTabAt(APPOINTMENT_JOINED))) {
                    viewPager.setCurrentItem(APPOINTMENT_JOINED);
                } else {
                    viewPager.setCurrentItem(APPOINTMENT_UN_JOINED);
                }
            }
        });
    }
}
