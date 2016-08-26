package com.example.aidong.activity.home;

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
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.ApplicantAdapter;
import com.example.aidong.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.support.entity.CampaignDetailBean;
import com.leyuan.support.entity.data.CampaignDetailData;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.mvp.model.impl.CampaignModelImpl;
import com.leyuan.support.util.FormatUtil;
import com.leyuan.support.widget.customview.ViewPagerIndicator;

/**
 * 活动详情
 * Created by song on 2016/8/24
 */
public class CampaignDetailActivity extends BaseActivity {

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    private ViewPager viewPager;
    private ViewPagerIndicator indicator;

    private RecyclerView recyclerView;
    private ApplicantAdapter applicantAdapter;

    private TextView tvHot;
    private TextView tvName;
    private TextView tvLandmark;
    private TextView tvTime;
    private TextView tvAddress;
    private TextView tvOrganizer;
    private TextView tvCount;
    private TextView tvDesc;
    private TextView tvPrice;

    private CampaignDetailBean bean;
    private String id ;


    /**
     * 跳转活动界面
     * @param id 活动id
     */
    public static void newInstance(Context context,String id){
        Intent intent = new Intent(context,CampaignDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_detail);

        initView();

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("id");
        }

        new CampaignModelImpl().getCampaignDetail(new ProgressSubscriber<CampaignDetailData>(this) {
            @Override
            public void onNext(CampaignDetailData campaignDetailData) {
                fillData(campaignDetailData);
            }
        }, FormatUtil.toParseInt(id));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
        return true;
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
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());

        recyclerView = (RecyclerView)findViewById(R.id.rv_applicant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        applicantAdapter = new ApplicantAdapter();
        recyclerView.setAdapter(applicantAdapter);

        tvName = (TextView)findViewById(R.id.tv_campaign_name);
        tvLandmark = (TextView)findViewById(R.id.tv_landmark);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvAddress = (TextView)findViewById(R.id.tv_address);
        tvOrganizer = (TextView)findViewById(R.id.tv_organizer);
        tvCount = (TextView)findViewById(R.id.tv_count);
        tvDesc = (TextView)findViewById(R.id.tv_campaign_desc);
        tvPrice = (TextView)findViewById(R.id.tv_price);
    }

    private void fillData(CampaignDetailData campaignDetailData) {
        if (campaignDetailData != null) {
            bean = campaignDetailData.getCampaign();
            tvName.setText(bean.getName());
            tvLandmark.setText(bean.getLandmark());
            tvTime.setText(bean.getStart_time());
            tvAddress.setText(bean.getAddress());
            tvOrganizer.setText(bean.getOrganizer());
            tvDesc.setText(bean.getIntroduce());
            applicantAdapter.setData(bean.getApplicant());
            tvCount.setText(String.format(getString(R.string.applicant_count),bean.getApplicant().size(),bean.getPlace()));
            tvPrice.setText(String.format(getString(R.string.rmb_price),bean.getPrice()));
        }
    }

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
}
