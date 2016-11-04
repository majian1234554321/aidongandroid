package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.ApplicantAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CampaignDetailActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.customview.ViewPagerIndicator;

/**
 * 活动详情
 * Created by song on 2016/8/24
 */
public class CampaignDetailActivity extends BaseActivity implements CampaignDetailActivityView,View.OnClickListener{
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;

    private SwitcherLayout switcherLayout;
    private RecyclerView recyclerView;
    private TextView tvHot;
    private TextView tvName;
    private TextView tvLandmark;
    private TextView tvTime;
    private TextView tvAddress;
    private TextView tvOrganizer;
    private TextView tvCount;
    private TextView tvDesc;
    private LinearLayout applyLayout;
    private TextView tvPrice;

    private String id ;                         //活动详情id
    private boolean isFinishLoad = false;       //数据是否完成加载
    private ApplicantAdapter applicantAdapter;
    private CampaignPresent campaignPresent;

    /**
     * 跳转活动界面
     * @param id 活动id
     */
    public static void start(Context context, String id){
        Intent intent = new Intent(context,CampaignDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_detail);

        campaignPresent = new CampaignPresentImpl(this,this);
        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("id");
        }



        initView();
        setListener();

        id = "1";
       // campaignPresent.getCampaignDetail(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return isFinishLoad;
    }

    private void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.vp_campaign);
        indicator = (ViewPagerIndicator)findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_tool_bar);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);

        recyclerView = (RecyclerView)findViewById(R.id.rv_applicant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        applicantAdapter = new ApplicantAdapter();
        recyclerView.setAdapter(applicantAdapter);

        switcherLayout = new SwitcherLayout(this,findViewById(R.id.ll_content));
        tvName = (TextView)findViewById(R.id.tv_campaign_name);
        tvLandmark = (TextView)findViewById(R.id.tv_landmark);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvAddress = (TextView)findViewById(R.id.tv_address);
        tvOrganizer = (TextView)findViewById(R.id.tv_organizer);
        tvCount = (TextView)findViewById(R.id.tv_count);
        tvDesc = (TextView)findViewById(R.id.tv_campaign_desc);
        applyLayout = (LinearLayout)findViewById(R.id.ll_apply);
        tvPrice = (TextView)findViewById(R.id.tv_price);
    }

    private void setListener() {
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());
        switcherLayout.setOnRetryListener(retryListener);
        toolbar.setNavigationOnClickListener(this);
        recyclerView.setOnClickListener(this);
        applyLayout.setOnClickListener(this);
    }

    //重试监听
    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            campaignPresent.getCampaignDetail(id);
        }
    };

    private  class MyOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener{
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            toolbar.setBackgroundColor(Color.argb((int) (percentage * 255), 0, 0, 0));

            if(Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()){
                collapsingToolbarLayout.setTitle(getString(R.string.campaign_detail));
            }else{
                collapsingToolbarLayout.setTitle("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar:          //回退
                finish();
                break;
            case R.id.rv_applicant:     //查看报名的人
                break;
            case R.id.ll_apply:         //报名
                break;
            default:
                break;
        }
    }

    @Override
    public void getCampaignDetail(CampaignDetailBean bean) {
        isFinishLoad = true;

        tvName.setText(bean.getName());
        tvLandmark.setText(bean.getLandmark());
        tvTime.setText(bean.getStart_time());
        tvAddress.setText(bean.getAddress());
        tvOrganizer.setText(bean.getOrganizer());
        tvDesc.setText(bean.getIntroduce());
        applicantAdapter.setData(bean.getApplicant());
        tvCount.setText(String.format(getString(R.string.applicant_count),bean.getApplicant().size(),bean.getPlace()));
        tvPrice.setText(String.format(getString(R.string.rmb_price),bean.getPrice()));
        applyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void shareCampaign() {

    }

    @Override
    public void applyCampaign() {

    }

    @Override
    public void showLoadingView() {
        switcherLayout.showLoadingLayout();
    }

    @Override
    public void showContent() {
        switcherLayout.showContentLayout();
    }

    @Override
    public void showNetErrorView() {
        switcherLayout.showExceptionLayout();
    }

    @Override
    public void showNoContentView() {
        switcherLayout.showEmptyLayout();
    }
}
