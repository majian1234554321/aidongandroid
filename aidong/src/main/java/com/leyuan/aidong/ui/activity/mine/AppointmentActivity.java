package com.leyuan.aidong.ui.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.TabFragmentAdapter;
import com.leyuan.aidong.ui.fragment.mine.AppointmentFragment;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

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

        final ArrayList<Fragment> fragments = new ArrayList<>();
        AppointmentFragment all = AppointmentFragment.newInstance(AppointmentFragment.ALL);
        AppointmentFragment joined = AppointmentFragment.newInstance(AppointmentFragment.JOINED);
        AppointmentFragment unJoined = AppointmentFragment.newInstance(AppointmentFragment.UN_JOIN);

        fragments.add(all);
        fragments.add(joined);
        fragments.add(unJoined);

        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.appointmentTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
