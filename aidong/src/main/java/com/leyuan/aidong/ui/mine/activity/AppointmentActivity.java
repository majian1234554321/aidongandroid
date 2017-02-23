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
import com.leyuan.aidong.ui.mine.fragment.AppointmentFragment;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的预约
 * Created by song on 2016/8/31.
 */
public class AppointmentActivity extends BaseActivity implements SmartTabLayout.TabProvider {
    private SimpleTitleBar titleBar;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private List<View> allTabView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);


        FragmentPagerItems pages = new FragmentPagerItems(this);
        AppointmentFragment all = new AppointmentFragment();
        AppointmentFragment joined = new AppointmentFragment();
        AppointmentFragment unJoined = new AppointmentFragment();
        pages.add(FragmentPagerItem.of(null, all.getClass(),
                new Bundler().putString("type",AppointmentFragment.ALL).get()));
        pages.add(FragmentPagerItem.of(null,joined.getClass(),
                new Bundler().putString("type",AppointmentFragment.JOINED).get()));
        pages.add(FragmentPagerItem.of(null,unJoined.getClass(),
                new Bundler().putString("type", AppointmentFragment.UN_JOIN).get()));
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
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_appointment_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.appointmentTab);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }
}
