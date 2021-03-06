package com.example.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong .entity.VenuesDetailBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.discover.fragment.VenuesCoachFragment;
import com.example.aidong .ui.discover.fragment.VenuesCourseNewFragment;
import com.example.aidong .ui.discover.fragment.VenuesDetailFragment;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .utils.Logger;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆详情
 * Created by song on 2016/9/21.
 */
public class VenuesDetailActivity extends BaseActivity implements SmartTabLayout.TabProvider {
    private static final java.lang.String TAG = "VenuesDetailActivity";
    private ImageView ivBack;
    private TextView tvAppointment;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    private String id;
    private VenuesDetailBean venuesBean;
    private List<View> allTabView = new ArrayList<>();
    private int currentPosition;
    //    private VenuesCourseNewFragment courseFragment;
    private FragmentPagerItemAdapter adapter;

    public static void start(Context context, String id) {
        Intent starter = new Intent(context, VenuesDetailActivity.class);
        starter.putExtra("id", id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_detail);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        tvAppointment = (TextView) findViewById(R.id.tv_appointment);

        FragmentPagerItems pages = new FragmentPagerItems(this);
        VenuesDetailFragment detail = new VenuesDetailFragment();
        VenuesCourseNewFragment courseFragment = new VenuesCourseNewFragment();
        VenuesCoachFragment coach = new VenuesCoachFragment();
        pages.add(FragmentPagerItem.of(null, detail.getClass(), new Bundler().putString("id", id).get()));
        pages.add(FragmentPagerItem.of(null, courseFragment.getClass(), new Bundler().putString("id", id).get()));
        pages.add(FragmentPagerItem.of(null, coach.getClass(), new Bundler().putString("id", id).get()));

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                }

                Logger.i(TAG, "position = " + position);
                if (position == 0) {
                    tvAppointment.setVisibility(View.VISIBLE);
                } else {
                    tvAppointment.setVisibility(View.GONE);
                }

                if (position == 1) {
                    Fragment page = adapter.getPage(1);
                    ((VenuesCourseNewFragment) page).freshData(venuesBean);
                }
                currentPosition = position;
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mInstance.isLogin()) {
                    AppointVenuesActivity.start(VenuesDetailActivity.this, id, venuesBean.getName(),
                            venuesBean.getAddress(), venuesBean.getTel());
                } else {
                    Toast.makeText(VenuesDetailActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(VenuesDetailActivity.this, LoginActivity.class));
                }
            }
        });
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_venues_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] title = getResources().getStringArray(R.array.venuesTab);
        text.setText(title[position]);
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

    public void loadFinish(VenuesDetailBean bean) {
        venuesBean = bean;

        if (currentPosition == 0) {
            tvAppointment.setVisibility(View.VISIBLE);
        }
//        courseFragment.freshData(venuesBean);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Fragment page = adapter.getPage(2);
//                ((VenuesCourseNewFragment) page).freshData(venuesBean);
//            }
//        },500);


    }
}
