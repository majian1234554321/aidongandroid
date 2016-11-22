package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.CouponActivity;
import com.leyuan.aidong.widget.customview.ExtendTextView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

/**
 * 预约信息 包括课程和活动的预约
 * Created by song on 2016/9/12.
 */
public class
AppointmentInfoActivity extends BaseActivity implements View.OnClickListener {
    public static final int TYPE_COURSE = 1;
    public static final int TYPE_CAMPAIGN = 2;

    private SimpleTitleBar titleBar;

    //预约人信息
    private EditText etInputName;
    private EditText etInputPhone;

    //课程或活动信息
    private TextView tvType;
    private SimpleDraweeView dvCover;
    private TextView tvName;
    private TextView tvShop;
    private ExtendTextView tvTime;
    private ExtendTextView tvAddress;
    private LinearLayout couponLayout;
    private LinearLayout goldLayout;

    //会员身份信息
    private TextView tvVip;
    private TextView tvNoVip;
    private LinearLayout vipTipLayout;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvCouponPrice;
    private ExtendTextView tvDiscountPrice;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;

    //支付方式
    private RadioButton cbAlipay;
    private RadioButton cbWeixin;

    //支付信息
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;

    private int type;   //区分课程或活动预约
    private CourseDetailBean courseDetailBean;
    private CampaignDetailBean campaignDetailBean;

    //from campaign detail activity
    public static void start(Context context, int type, CampaignDetailBean campaignDetailBean) {
        Intent starter = new Intent(context, AppointmentInfoActivity.class);
        starter.putExtra("type", type);
        starter.putExtra("bean", campaignDetailBean);
        context.startActivity(starter);
    }

    // form course detail activity
    public static void start(Context context, int type, CourseDetailBean courseDetailBean) {
        Intent starter = new Intent(context, AppointmentInfoActivity.class);
        starter.putExtra("type", type);
        starter.putExtra("bean", courseDetailBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", TYPE_COURSE);
            if (type == TYPE_COURSE) {
                courseDetailBean = getIntent().getParcelableExtra("bean");
            } else {
                campaignDetailBean = getIntent().getParcelableExtra("bean");
            }
        }
        initView();
        setListener();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        etInputName = (EditText) findViewById(R.id.et_input_name);
        etInputPhone = (EditText) findViewById(R.id.et_input_phone);
        tvType = (TextView) findViewById(R.id.tv_type);
        dvCover = (SimpleDraweeView) findViewById(R.id.dv_cover);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvShop = (TextView) findViewById(R.id.tv_shop);
        tvTime = (ExtendTextView) findViewById(R.id.tv_time);
        tvAddress = (ExtendTextView) findViewById(R.id.tv_address);
        couponLayout = (LinearLayout) findViewById(R.id.ll_coupon);
        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
        tvVip = (TextView) findViewById(R.id.tv_vip);
        tvNoVip = (TextView) findViewById(R.id.tv_no_vip);
        vipTipLayout = (LinearLayout) findViewById(R.id.ll_vip_tip);
        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.tv_coupon_price);
        tvDiscountPrice = (ExtendTextView) findViewById(R.id.tv_discount_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        cbAlipay = (RadioButton) findViewById(R.id.cb_alipay);
        cbWeixin = (RadioButton) findViewById(R.id.cb_weixin);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        tvType.setText(TYPE_COURSE == type ? getString(R.string.course_info) : getString(R.string.campaign_info));
        tvName.setText(TYPE_COURSE == type ? courseDetailBean.getName() : campaignDetailBean.getName());
        tvTime.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_time) : getString(R.string.campaign_time));
        tvTime.setRightTextContent(TYPE_COURSE == type ? courseDetailBean.getClass_time() : campaignDetailBean.getStart_time());
        tvAddress.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_address) : getString(R.string.campaign_address));
        tvAddress.setRightTextContent(TYPE_COURSE == type ? courseDetailBean.getAddress() : campaignDetailBean.getAddress());
        tvTotalPrice.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_price) : getString(R.string.campaign_price));
        tvTotalPrice.setRightTextContent(TYPE_COURSE == type ? courseDetailBean.getPrice() : campaignDetailBean.getPrice());
        tvDiscountPrice.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_privilege) : getString(R.string.campaign_privilege));
        tvDiscountPrice.setRightTextContent("0");
        tvPrice.setText(TYPE_COURSE == type ? courseDetailBean.getPrice() : campaignDetailBean.getPrice());
    }


    private void setListener() {
        etInputName.setText("song");
        etInputPhone.setText("123455");
        couponLayout.setOnClickListener(this);
        tvVip.setOnClickListener(this);
        tvNoVip.setOnClickListener(this);
        tvPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_coupon:
                startActivity(new Intent(this, CouponActivity.class));
                break;
            case R.id.tv_vip:
                tvNoVip.setTextColor(Color.parseColor("#000000"));
                tvVip.setTextColor(Color.parseColor("#ffffff"));
                tvNoVip.setBackgroundResource(R.drawable.shape_bg_white);
                tvVip.setBackgroundResource(R.drawable.shape_bg_black);
                vipTipLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_no_vip:
                tvVip.setTextColor(Color.parseColor("#000000"));
                tvNoVip.setTextColor(Color.parseColor("#ffffff"));
                tvVip.setBackgroundResource(R.drawable.shape_bg_white);
                tvNoVip.setBackgroundResource(R.drawable.shape_bg_black);
                vipTipLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pay:
                startActivity(new Intent(this, AppointmentSuccessActivity.class));
                break;
            default:
                break;
        }
    }
}
