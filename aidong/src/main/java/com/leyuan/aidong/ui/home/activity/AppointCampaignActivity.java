package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointCampaignActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_COUPON;

/**
 * 预约活动
 * Created by song on 2016/9/12.
 */
//todo 与课程预约界面合成一个界面
public class AppointCampaignActivity extends BaseActivity implements View.OnClickListener,
        CustomNestRadioGroup.OnCheckedChangeListener ,AppointCampaignActivityView{
    private SimpleTitleBar titleBar;

    //预约人信息
    private TextView tvUserName;
    private TextView tvUserPhone;

    //活动信息
    private TextView tvType;
    private ImageView dvCover;
    private TextView tvCampaignName;
    private TextView tvOrganizer;
    private ExtendTextView tvTime;
    private ExtendTextView tvAddress;
    private TextView tvCoupon;
    private LinearLayout goldLayout;

    //会员身份信息
    private TextView tvVip;
    private TextView tvNoVip;
    private TextView tvNoVipTip;
    private LinearLayout vipTipLayout;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvCouponPrice;
    private ExtendTextView tvDiscountPrice;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;

    //支付
    private CustomNestRadioGroup radioGroup;
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;

    private String couponId;
    private float integral ;
    private String payType;
    private String userName;
    private String contactMobile;

    private CampaignDetailBean bean;
    private CampaignPresent campaignPresent;
    private List<CouponBean> usableCoupons = new ArrayList<>();

    public static void start(Context context,CampaignDetailBean campaignBean) {
        Intent starter = new Intent(context, AppointCampaignActivity.class);
        starter.putExtra("bean", campaignBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_campaign);
        campaignPresent = new CampaignPresentImpl(this,this);
        payType = PayType.ALI;
        if (getIntent() != null) {
            bean = getIntent().getParcelableExtra("bean");
        }
        initView();
        setListener();
        campaignPresent.getSpecifyCampaignCoupon(bean.getCampaignId());
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvUserName = (TextView) findViewById(R.id.tv_input_name);
        tvUserPhone = (TextView) findViewById(R.id.tv_input_phone);
        tvType = (TextView) findViewById(R.id.tv_type);
        dvCover = (ImageView) findViewById(R.id.dv_cover);
        tvCampaignName = (TextView) findViewById(R.id.tv_name);
        tvOrganizer = (TextView) findViewById(R.id.tv_organizer);
        tvTime = (ExtendTextView) findViewById(R.id.tv_time);
        tvAddress = (ExtendTextView) findViewById(R.id.tv_address);
        tvCoupon = (TextView) findViewById(R.id.tv_coupon);
        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
        tvVip = (TextView) findViewById(R.id.tv_vip);
        tvNoVip = (TextView) findViewById(R.id.tv_no_vip);
        tvNoVipTip = (TextView) findViewById(R.id.tv_vip_tip);
        vipTipLayout = (LinearLayout) findViewById(R.id.ll_vip_tip);
        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.tv_coupon_price);
        tvDiscountPrice = (ExtendTextView) findViewById(R.id.tv_discount_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        userName = App.mInstance.getUser().getName();
        contactMobile = App.mInstance.getUser().getMobile();
        tvUserName.setText(userName);
        tvUserPhone.setText(contactMobile);
        tvCampaignName.setText(bean.getName());
        tvOrganizer.setText(bean.getOrganizer());
        GlideLoader.getInstance().displayImage(bean.getImage().get(0), dvCover);
        tvTime.setRightContent(String.format(getString(R.string.campaign_during), bean.getStartTime(),
                bean.getEndTime().substring(bean.getEndTime().lastIndexOf(Constant.EMPTY_STR))));
        tvAddress.setRightContent(bean.getAddress());
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price), bean.getPrice()));
        tvPrice.setText(String.format(getString(R.string.rmb_price), bean.getPrice()));
        tvNoVipTip.setText(Html.fromHtml(getString(R.string.no_vip_tip)));
    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        tvCoupon.setOnClickListener(this);
        tvVip.setOnClickListener(this);
        tvNoVip.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_coupon:
                if(usableCoupons != null && !usableCoupons.isEmpty()) {
                    Intent intent = new Intent(this, SelectCouponActivity.class);
                    startActivityForResult(intent, REQUEST_SELECT_COUPON);
                }
                break;
            case R.id.tv_vip:
                tvNoVip.setTextColor(Color.parseColor("#000000"));
                tvVip.setTextColor(Color.parseColor("#ffffff"));
                tvNoVip.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvVip.setBackgroundResource(R.drawable.shape_solid_corner_black);
                vipTipLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_no_vip:
                tvVip.setTextColor(Color.parseColor("#000000"));
                tvNoVip.setTextColor(Color.parseColor("#ffffff"));
                tvVip.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvNoVip.setBackgroundResource(R.drawable.shape_solid_corner_black);
                vipTipLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pay:
                campaignPresent.buyCampaign(bean.getCampaignId(),couponId,integral,
                        payType,userName,contactMobile,payListener);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFreeCampaignAppointed() {
        AppointSuccessActivity.start(this,bean.getStartTime());
        Toast.makeText(AppointCampaignActivity.this,"预约成功",Toast.LENGTH_LONG).show();
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            AppointSuccessActivity.start(AppointCampaignActivity.this,bean.getStartTime());
            Toast.makeText(AppointCampaignActivity.this,"支付成功",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.cb_alipay:
                payType = PayType.ALI;
                break;
            case R.id.cb_weixin:
                payType = PayType.WEIXIN;
                break;
            default:
                break;
        }
    }

    @Override
    public void setCampaignCouponResult(List<CouponBean> usableCoupons) {
        this.usableCoupons = usableCoupons;
        if(usableCoupons == null || usableCoupons.isEmpty()){
            tvCoupon.setText("无可用");
            tvCoupon.setCompoundDrawables(null,null,null,null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            if(requestCode == REQUEST_SELECT_COUPON){
                CouponBean couponBean = data.getParcelableExtra("coupon");
                tvCoupon.setText(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getDiscount())));
                tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getDiscount())));
                tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                        FormatUtil.parseDouble(bean.getPrice()) - FormatUtil.parseDouble(couponBean.getDiscount())));

            }
        }
    }
}
