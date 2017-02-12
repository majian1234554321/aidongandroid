package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.CouponActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentInfoActivityView;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;

/**
 * 预约信息 包括课程和活动的预约
 * Created by song on 2016/9/12.
 */
@Deprecated
//todo 还是拆成两个类吧...
public class AppointInfoActivity extends BaseActivity implements View.OnClickListener, AppointmentInfoActivityView, CustomNestRadioGroup.OnCheckedChangeListener {
    public static final int TYPE_COURSE = 1;
    public static final int TYPE_CAMPAIGN = 2;
    private static final String ALI_PAY = "alipay";
    private static final String WEI_XIN_PAY = "wxpay";

    private SimpleTitleBar titleBar;

    //预约人信息
    private TextView tvUserName;
    private TextView tvUserPhone;

    //课程或活动信息
    private TextView tvType;
    private SimpleDraweeView dvCover;
    private TextView tvCourseName;
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

    //支付
    private CustomNestRadioGroup radioGroup;
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;

    private String couponId;
    private String integral;
    private String payType;
    private String userName;
    private String contactMobile;
    private int type;   //区分课程或活动预约
    private CourseDetailBean courseDetailBean;
    private CampaignDetailBean campaignDetailBean;
    private CoursePresent coursePresent;

    //from campaign detail activity
    public static void start(Context context, int type, CampaignDetailBean campaignDetailBean) {
        Intent starter = new Intent(context, AppointInfoActivity.class);
        starter.putExtra("type", type);
        starter.putExtra("bean", campaignDetailBean);
        context.startActivity(starter);
    }

    // form course detail activity
    public static void start(Context context, int type, CourseDetailBean courseDetailBean) {
        Intent starter = new Intent(context, AppointInfoActivity.class);
        starter.putExtra("type", type);
        starter.putExtra("bean", courseDetailBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);
        payType = ALI_PAY;
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
        tvUserName = (TextView) findViewById(R.id.tv_input_name);
        tvUserPhone = (TextView) findViewById(R.id.tv_input_phone);
        tvType = (TextView) findViewById(R.id.tv_type);
        dvCover = (SimpleDraweeView) findViewById(R.id.dv_cover);
        tvCourseName = (TextView) findViewById(R.id.tv_name);
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
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        userName = App.mInstance.getUser().getName();
        contactMobile = App.mInstance.getUser().getMobile();
        tvUserName.setText(userName);
        tvUserPhone.setText(contactMobile);
        tvType.setText(TYPE_COURSE == type ? getString(R.string.course_info) : getString(R.string.campaign_info));
        tvCourseName.setText(TYPE_COURSE == type ? courseDetailBean.getName() : campaignDetailBean.getName());
        tvTime.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_time) : getString(R.string.campaign_time));
        tvTime.setRightContent(TYPE_COURSE == type ? courseDetailBean.getClassTime() : campaignDetailBean.getStartTime());
        tvAddress.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_address) : getString(R.string.campaign_address));
        tvAddress.setRightContent(TYPE_COURSE == type ? courseDetailBean.getAddress() : campaignDetailBean.getAddress());
        tvTotalPrice.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_price) : getString(R.string.campaign_price));
        tvTotalPrice.setRightContent(TYPE_COURSE == type ? courseDetailBean.getPrice() : campaignDetailBean.getPrice());
        tvDiscountPrice.setLeftTextContent(TYPE_COURSE == type ? getString(R.string.course_privilege) : getString(R.string.campaign_privilege));
        tvDiscountPrice.setRightContent("0");
        tvPrice.setText(TYPE_COURSE == type ? courseDetailBean.getPrice() : campaignDetailBean.getPrice());
    }

    private void setListener() {
        couponLayout.setOnClickListener(this);
        tvVip.setOnClickListener(this);
        tvNoVip.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
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
                tvNoVip.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvVip.setBackgroundResource(R.drawable.shape_solid_black);
                vipTipLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_no_vip:
                tvVip.setTextColor(Color.parseColor("#000000"));
                tvNoVip.setTextColor(Color.parseColor("#ffffff"));
                tvVip.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvNoVip.setBackgroundResource(R.drawable.shape_solid_black);
                vipTipLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pay:
                if(coursePresent == null){
                    coursePresent = new CoursePresentImpl(this);
                }
                coursePresent.buyCourse(courseDetailBean.getCode(),couponId,integral,payType,
                        userName,contactMobile,payListener);
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new PayInterface.PayListener() {
        @Override
        public void fail(String code, Object object) {
            Toast.makeText(AppointInfoActivity.this,"failed:" + code + object.toString(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void success(String code, Object object) {
            Toast.makeText(AppointInfoActivity.this,"success:" + code + object.toString(),Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.cb_alipay:
                payType = ALI_PAY;
                break;
            case R.id.cb_weixin:
                payType = WEI_XIN_PAY;
                break;
            default:
                break;
        }
    }
}
