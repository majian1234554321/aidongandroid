package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesDetailBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.fragment.VenuesCoachFragment;
import com.leyuan.aidong.ui.discover.fragment.VenuesCourseFragment;
import com.leyuan.aidong.ui.discover.fragment.VenuesDetailFragment;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
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
public class VenuesDetailActivity extends BaseActivity implements SmartTabLayout.TabProvider{
    private ImageView ivBack;
    private TextView tvAppointment;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    private String id;
    private VenuesDetailBean venuesBean;
    private List<View> allTabView = new ArrayList<>();

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
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        tvAppointment = (TextView) findViewById(R.id.tv_appointment);

        FragmentPagerItems pages = new FragmentPagerItems(this);
        VenuesDetailFragment detail = new VenuesDetailFragment();
        VenuesCourseFragment course = new VenuesCourseFragment();
        VenuesCoachFragment coach = new VenuesCoachFragment();
        pages.add(FragmentPagerItem.of(null,detail.getClass(), new Bundler().putString("id", id).get()));
        pages.add(FragmentPagerItem.of(null,course.getClass(), new Bundler().putString("id", id).get()));
        pages.add(FragmentPagerItem.of(null,coach.getClass(), new Bundler().putString("id", id).get()));

        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
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

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.mInstance.isLogin()) {
                    AppointVenuesActivity.start(VenuesDetailActivity.this, id, venuesBean.getName(),
                            venuesBean.getAddress(), venuesBean.getTel());
                }else {
                    Toast.makeText(VenuesDetailActivity.this,"请先登陆",Toast.LENGTH_LONG).show();
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
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

    public void loadFinish(VenuesDetailBean bean){
        venuesBean = bean;
        tvAppointment.setVisibility(View.VISIBLE);
    }
}
