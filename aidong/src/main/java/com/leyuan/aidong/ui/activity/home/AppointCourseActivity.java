package com.leyuan.aidong.ui.activity.home;

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
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.CouponActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.widget.customview.CustomNestRadioGroup;
import com.leyuan.aidong.widget.customview.ExtendTextView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

/**
 * 预约课程信息
 * Created by song on 2016/9/12.
 */
public class AppointCourseActivity extends BaseActivity implements View.OnClickListener,CustomNestRadioGroup.OnCheckedChangeListener {
    private static final String ALI_PAY = "alipay";
    private static final String WEI_XIN_PAY = "wxpay";

    private SimpleTitleBar titleBar;

    //预约人信息
    private TextView tvUserName;
    private TextView tvUserPhone;

    //课程信息
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

    private CourseDetailBean courseDetailBean;
    private CoursePresent coursePresent;

    public static void start(Context context,CourseDetailBean courseDetailBean) {
        Intent starter = new Intent(context, AppointCourseActivity.class);
        starter.putExtra("bean", courseDetailBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_course);
        payType = ALI_PAY;
        if (getIntent() != null) {
            courseDetailBean = getIntent().getParcelableExtra("bean");
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
        dvCover.setImageURI(courseDetailBean.getCover());
        tvCourseName.setText(courseDetailBean.getName());
        tvTime.setRightTextContent(courseDetailBean.getClassTime());
        tvAddress.setRightTextContent(courseDetailBean.getGym().getAddress());
        tvTotalPrice.setRightTextContent(courseDetailBean.getPrice());
        tvDiscountPrice.setRightTextContent("0");
        tvPrice.setText(courseDetailBean.getPrice());
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
                tvNoVip.setBackgroundResource(R.drawable.shape_stroke_white);
                tvVip.setBackgroundResource(R.drawable.shape_solid_black);
                vipTipLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_no_vip:
                tvVip.setTextColor(Color.parseColor("#000000"));
                tvNoVip.setTextColor(Color.parseColor("#ffffff"));
                tvVip.setBackgroundResource(R.drawable.shape_stroke_white);
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
            Toast.makeText(AppointCourseActivity.this,"failed:" + code + object.toString(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void success(String code, Object object) {
            Toast.makeText(AppointCourseActivity.this,"success:" + code + object.toString(),Toast.LENGTH_LONG).show();
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
