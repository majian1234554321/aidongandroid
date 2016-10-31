package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.ApplicantAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.widget.customview.ViewPagerIndicator;

import java.util.ArrayList;

/**
 * 课程详情
 * Created by song on 2016/8/17.
 */
public class CourseDetailActivity extends BaseActivity implements View.OnClickListener {
    private CoordinatorLayout mainContent;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    private TextView tvHot;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;
    private TextView tvCoachName;
    private ImageView ivFollow;
    private TextView tvTime;
    private TextView tvAddress;
    private TextView tvVenues;
    private TextView tvRoom;
    private TextView tvCount;
    private RecyclerView rvApplicant;
    private TextView tvDesc;
    private SimpleDraweeView avatar;
    private TextView tvPrice;
    private TextView tvState;

    private ApplicantAdapter applicantAdapter;
    private String id;

    public static void start(Context context, String id){
        Intent intent = new Intent(context,CourseDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_new);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
        }

        initView();
        setListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
        return true;
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mainContent = (CoordinatorLayout) findViewById(R.id.main_content);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_campaign);
        indicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);
        tvHot = (TextView) findViewById(R.id.tv_hot);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scrollView);
        tvCoachName = (TextView) findViewById(R.id.tv_coach_name);
        ivFollow = (ImageView) findViewById(R.id.iv_follow);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvVenues = (TextView) findViewById(R.id.tv_venues);
        tvRoom = (TextView) findViewById(R.id.tv_room);
        tvCount = (TextView) findViewById(R.id.tv_count);
        rvApplicant = (RecyclerView) findViewById(R.id.rv_applicant);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        avatar = (SimpleDraweeView) findViewById(R.id.dv_avatar);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

        applicantAdapter = new ApplicantAdapter();
        rvApplicant.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        applicantAdapter = new ApplicantAdapter();
        rvApplicant.setAdapter(applicantAdapter);
        rvApplicant.setNestedScrollingEnabled(false);

        applicantAdapter.setData(null);
    }


    private void setListener(){
        avatar.setOnClickListener(this);
        ivFollow.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvCount.setOnClickListener(this);
        tvState.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dv_avatar:
                break;
            case R.id.iv_follow:
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_count:
                AppointmentUserActivity.start(this,new ArrayList<UserBean>());
                break;
            case R.id.tv_state:
                startActivity(new Intent(this,AppointmentInfoActivity.class));
                break;
            default:
                break;
        }
    }

    private  class MyOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener{
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            toolbar.setBackgroundColor(Color.argb((int) (percentage * 255), 0, 0, 0));
            if(maxScroll - Math.abs(verticalOffset) < DensityUtil.dp2px(CourseDetailActivity.this,35)){
               avatar.setVisibility(View.GONE);
            }else {
                avatar.setVisibility(View.VISIBLE);
            }
        }
    }
}
