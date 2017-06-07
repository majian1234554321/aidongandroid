package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ApplicantAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AppointCampaignDetailActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CampaignDetailActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.zzhoujay.richtext.RichText;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.leyuan.aidong.R.id.iv_back;


/**
 * 活动详情
 * Created by song on 2016/8/24
 */
public class CampaignDetailActivity extends BaseActivity implements CampaignDetailActivityView, View.OnClickListener {
    private static final String STATUS_APPLY = "1";                //马上报名
    private static final String STATUS_NOT_START = "2";            //即将开始报名
    private static final String STATUS_APPOINT_END = "3";          //报名已结束
    private static final String STATUS_APPLIED = "4";              //已报名
    private static final String STATUS_NOT_PAY = "5";              //待支付
    private static final String STATUS_FULL = "6";                 //报名人数已满
    private static final String STATUS_CAMPAIGN_END = "7";         //活动已结束

    private ImageView ivBack;
    private ImageView ivShare;
    private TextView tvTopStartTime;
    private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;
    private RelativeLayout pagerLayout;
    private Toolbar toolbar;
    private BGABanner bannerLayout;
    private TextView tvHot;
    private TextView tvCampaignName;
    private TextView tvLandmark;
    private TextView tvStartTime;

    private LinearLayout addressLayout;
    private TextView tvAddress;
    private TextView tvOrganizer;
    private TextView tvCount;
    private RecyclerView applicantView;
    private TextView tvCampaignDesc;
    private LinearLayout bottomLayout;
    private TextView tvPrice;
    private TextView tvState;

    private String id;                         //活动详情id
    private ApplicantAdapter applicantAdapter;
    private CampaignPresent campaignPresent;
    private CampaignDetailBean bean;
    private SharePopupWindow sharePopupWindow;

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, CampaignDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_detail);
        campaignPresent = new CampaignPresentImpl(this, this);
        sharePopupWindow = new SharePopupWindow(this);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }
        initView();
        setListener();
        campaignPresent.getCampaignDetail(switcherLayout, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(this);
        sharePopupWindow.release();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        tvTopStartTime = (TextView) findViewById(R.id.tv_start_time_tip);
        pagerLayout = (RelativeLayout) findViewById(R.id.rl_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bannerLayout = (BGABanner) findViewById(R.id.banner_layout);
        tvHot = (TextView) findViewById(R.id.tv_hot);
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this, contentLayout);
        tvCampaignName = (TextView) findViewById(R.id.tv_campaign_name);
        tvLandmark = (TextView) findViewById(R.id.tv_landmark);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvOrganizer = (TextView) findViewById(R.id.tv_organizer);
        tvCount = (TextView) findViewById(R.id.tv_count);
        applicantView = (RecyclerView) findViewById(R.id.rv_applicant);
        tvCampaignDesc = (TextView) findViewById(R.id.tv_campaign_desc);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_apply);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);

        bannerLayout.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage((String) model, (ImageView) view);
            }
        });

        applicantView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));
        applicantAdapter = new ApplicantAdapter(this);
        applicantView.setAdapter(applicantAdapter);
        applicantView.setNestedScrollingEnabled(false);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        switcherLayout.setOnRetryListener(retryListener);
        bottomLayout.setOnClickListener(this);
        tvCount.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
    }

    //重试监听
    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            campaignPresent.getCampaignDetail(switcherLayout, id);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case iv_back:               //回退
                finish();
                break;
            case R.id.iv_share:
                if (bean != null) {
                    String image = "";
                    if (bean.getImage() != null && !bean.getImage().isEmpty()) {
                        image = bean.getImage().get(0);
                    }
                    sharePopupWindow.showAtBottom(bean.getName()+Constant.I_DONG_FITNESS, bean.getIntroduce(),
                            image, ConstantUrl.URL_SHARE_CAMPAIGN + bean.getCampaignId());
                }

                break;
            case R.id.tv_count:         //查看报名的人
                AppointmentUserActivity.start(this, bean.getApplicant());
                break;
            case R.id.ll_apply:         //报名
                bottomToTargetActivity();
                break;
            case R.id.ll_address:
                MapActivity.start(this, "地址详情", bean.getAddress(), bean.getLandmark(),
                        bean.getCoordinate().getLat(), bean.getCoordinate().getLng());
                break;
            default:
                break;
        }
    }

    @Override
    public void setCampaignDetail(final CampaignDetailBean bean) {
        this.bean = bean;
        pagerLayout.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.VISIBLE);
        bannerLayout.setData(bean.getImage(), null);
        tvHot.setText(bean.getViewCount());
        tvCampaignName.setText(bean.getName());
        tvLandmark.setText(bean.getLandmark());
        tvStartTime.setText(bean.getStartTime());
        tvAddress.setText(bean.getAddress());
        tvOrganizer.setText(bean.getOrganizer());
        applicantAdapter.setData(bean.getApplicant());
        if (!TextUtils.isEmpty(bean.getIntroduce())) {
            RichText.from(bean.getIntroduce()).placeHolder(R.drawable.place_holder_logo)
                    .error(R.drawable.place_holder_logo).into(tvCampaignDesc);
        }
        if (bean.getApplicant() == null || bean.getApplicant().isEmpty()) {
            tvCount.setText(String.format(getString(R.string.applicant_count), 0, bean.getPlace()));
        } else {
            tvCount.setText(String.format(getString(R.string.applicant_count)
                    , bean.getApplicant().size(), bean.getPlace()));
        }
        tvPrice.setText(FormatUtil.parseDouble(bean.getPrice()) == 0f
                ? "免费" : String.format(getString(R.string.rmb_price), bean.getPrice()));

        tvTopStartTime.setText(String.format(getString(R.string.apply_time), bean.getEntry_start_time()));
        tvTopStartTime.setVisibility(STATUS_NOT_START.equals(bean.getStatus()) ? View.VISIBLE : View.GONE);
        toolbar.getLayoutParams().height = DensityUtil.dp2px(this, STATUS_NOT_START.equals(bean.getStatus()) ? 76 : 46);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) pagerLayout.getLayoutParams();
        params.setMargins(0, DensityUtil.dp2px(this, STATUS_NOT_START.equals(bean.getStatus()) ? 76 : 46), 0, 0);
        pagerLayout.setLayoutParams(params);
        setBottomStatus();

        if(bean.getStatus().equals(STATUS_NOT_START)){
            CountDownTimer timer = new CountDownTimer(DateUtils.getCounterDown(bean.getEntry_start_time()) ,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    bean.setStatus(STATUS_APPLY);
                    setCampaignDetail(bean);
                }
            };
            timer.start();// 开始计时
        }
    }

    //设置底部状态
    private void setBottomStatus() {
        if (TextUtils.isEmpty(bean.getStatus())) {
            return;
        }
        bottomLayout.setVisibility(View.VISIBLE);
        switch (bean.getStatus()) {
            case STATUS_APPLY:
                tvPrice.setVisibility(View.VISIBLE);
                tvState.setText(R.string.campaign_status_apply);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_CAMPAIGN_END:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_end);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_APPOINT_END:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_appoint_end);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_APPLIED:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_applied);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_NOT_START:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_not_start);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NOT_PAY:
                tvPrice.setVisibility(View.VISIBLE);
                tvState.setText(R.string.campaign_status_not_pay);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_FULL:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_full);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            default:
                break;
        }
    }

    private void bottomToTargetActivity() {
        if (STATUS_APPLY.equals(bean.getStatus())) {     //预约
            if (App.mInstance.isLogin()) {
                AppointCampaignActivity.start(this, bean);
            } else {
                startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
            }
        } else if (STATUS_NOT_PAY.equals(bean.getStatus())) {
            AppointCampaignDetailActivity.start(this, bean.getCampaignId(), true);
        } else if (STATUS_APPLIED.equals(bean.getStatus())) {
            AppointCampaignDetailActivity.start(this, bean.getCampaignId(), true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_LOGIN) {
                campaignPresent.getCampaignDetail(switcherLayout, id);
            }
        }
    }
}
