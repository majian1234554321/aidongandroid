package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.entity.PayOrderBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.ApplicantAdapter;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CampaignDetailActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 活动详情
 * Created by song on 2016/8/24
 */
public class CampaignDetailActivity extends BaseActivity implements CampaignDetailActivityView,View.OnClickListener{
    private AppBarLayout appBarLayout;
    private BGABanner bannerLayout;
    private TextView tvHot;
    private Toolbar toolbar;

    private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;
    private TextView tvCampaignName;
    private TextView tvLandmark;
    private TextView tvDate;
    private TextView tvTime;
    private LinearLayout addressLayout;
    private TextView tvAddress;
    private TextView tvOrganizer;
    private TextView tvCount;
    private RecyclerView applicantView;
    private TextView tvCampaignDesc;
    private LinearLayout applyLayout;
    private TextView tvPrice;
    private TextView tvState;

    private String id ;                         //活动详情id
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
        campaignPresent.getCampaignDetail(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
        return true;
    }

    private void initView(){
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        bannerLayout = (BGABanner) findViewById(R.id.banner_layout);
        tvHot = (TextView) findViewById(R.id.tv_hot);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);
        tvCampaignName = (TextView) findViewById(R.id.tv_campaign_name);
        tvLandmark = (TextView) findViewById(R.id.tv_landmark);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTime = (TextView) findViewById(R.id.tv_time);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvOrganizer = (TextView) findViewById(R.id.tv_organizer);
        tvCount = (TextView) findViewById(R.id.tv_count);
        applicantView = (RecyclerView) findViewById(R.id.rv_applicant);
        tvCampaignDesc = (TextView) findViewById(R.id.tv_campaign_desc);
        applyLayout = (LinearLayout) findViewById(R.id.ll_apply);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);
        applyLayout.setVisibility(View.GONE);

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        bannerLayout.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageLoader.getInstance().displayImage((String)model,(ImageView)view);
            }
        });

        applicantView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        applicantAdapter = new ApplicantAdapter();
        applicantView.setAdapter(applicantAdapter);
        applicantView.setNestedScrollingEnabled(false);
    }

    private void setListener() {
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());
        switcherLayout.setOnRetryListener(retryListener);
        toolbar.setNavigationOnClickListener(this);
        applyLayout.setOnClickListener(this);
        tvCount.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
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
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar:          //回退
                finish();
                break;
            case R.id.tv_count:         //查看报名的人
                AppointmentUserActivity.start(this,new ArrayList<UserBean>());
                break;
            case R.id.ll_apply:         //报名
                Intent intent = new Intent(this,AppointmentInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void setCampaignDetail(CampaignDetailBean bean) {
        applyLayout.setVisibility(View.VISIBLE);
        bannerLayout.setData(bean.getImage(),null);
        tvCampaignName.setText(bean.getName());
        tvLandmark.setText(bean.getLandmark());
        tvTime.setText(bean.getStart_time());
        tvAddress.setText(bean.getAddress());
        tvOrganizer.setText(bean.getOrganizer());
        tvCampaignDesc.setText(bean.getIntroduce());
        applicantAdapter.setData(bean.getApplicant());
        if(bean.getApplicant() == null || bean.getApplicant().isEmpty()){
            tvCount.setText(String.format(getString(R.string.applicant_count), 0,bean.getPlace()));
        }else{
            tvCount.setText(String.format(getString(R.string.applicant_count)
                    ,bean.getApplicant().size(),bean.getPlace()));
        }
        tvPrice.setText(String.format(getString(R.string.rmb_price),bean.getPrice()));
    }

    @Override
    public void onBuyCampaign(PayOrderBean payOrderBean) {

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
