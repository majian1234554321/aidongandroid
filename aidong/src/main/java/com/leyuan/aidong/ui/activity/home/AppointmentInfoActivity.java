package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.customview.ExtendTextView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

/**
 * 预约信息 包括课程和活动的预约
 * Created by song on 2016/9/12.
 */
public class AppointmentInfoActivity extends BaseActivity implements View.OnClickListener{
    public static final int TYPE_COURSE = 1;
    public static final int TYPE_CAMPAIGN = 2;

    private SimpleTitleBar titleBar;

    //预约人信息
    private EditText etInputName;
    private EditText etInputPhone;

    //课程或活动信息
    private TextView tvType;
    private SimpleDraweeView dvCover;
    private TextView tvCourseName;
    private TextView tvShop;
    private ExtendTextView tvCourseTime;
    private ExtendTextView tvCourseAddress;
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
    private String name;
    private String time;
    private String address;

    public static void start(Context context,int type,String name,String time,String address) {
        Intent starter = new Intent(context, AppointmentInfoActivity.class);
        starter.putExtra("type",type);
        starter.putExtra("name",name);
        starter.putExtra("time",time);
        starter.putExtra("address",address);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);
        if(getIntent() != null){
            type = getIntent().getIntExtra("type",TYPE_COURSE);
            name = getIntent().getStringExtra("name");
            time = getIntent().getStringExtra("time");
            address = getIntent().getStringExtra("address");
        }
        initView();
        setListener();
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        etInputName = (EditText) findViewById(R.id.et_input_name);
        etInputPhone = (EditText) findViewById(R.id.et_input_phone);
        tvType = (TextView)findViewById(R.id.tv_type);
        dvCover = (SimpleDraweeView) findViewById(R.id.dv_cover);
        tvCourseName = (TextView) findViewById(R.id.tv_course_name);
        tvShop = (TextView) findViewById(R.id.tv_shop);
        tvCourseTime = (ExtendTextView) findViewById(R.id.tv_course_time);
        tvCourseAddress = (ExtendTextView) findViewById(R.id.tv_course_address);
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

        tvType.setText(TYPE_COURSE == type ? getString(R.string.course_info) :
                getString(R.string.campaign_info));
        tvCourseName.setText(name);
        tvCourseTime.setRightTextContent(time);
        tvCourseAddress.setRightTextContent(address);
    }


    private void setListener() {
        etInputName.setText("song");
        etInputPhone.setText("123455");
        tvPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pay:
                startActivity(new Intent(this,AppointmentSuccessActivity.class));
                break;
            default:
                break;
        }
    }
}
