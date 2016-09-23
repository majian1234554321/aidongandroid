package com.example.aidong.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.widget.customview.ExtendTextView;
import com.leyuan.support.widget.customview.SimpleTitleBar;

/**
 * 预约信息
 * Created by song on 2016/9/12.
 */
public class AppointmentInfoActivity extends BaseActivity implements View.OnClickListener{

    private SimpleTitleBar titleBar;

    //课程或活动信息
    private EditText etInputName;
    private EditText etInputPhone;
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
    private ExtendTextView tvExpressPrice;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);

        initView();
        setListener();
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        etInputName = (EditText) findViewById(R.id.et_input_name);
        etInputPhone = (EditText) findViewById(R.id.et_input_phone);
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
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.tv_coupon_price);
        tvDiscountPrice = (ExtendTextView) findViewById(R.id.tv_discount_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        cbAlipay = (RadioButton) findViewById(R.id.cb_alipay);
        cbWeixin = (RadioButton) findViewById(R.id.cb_weixin);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);
    }


    private void setListener() {
        etInputName.setText("song");
        etInputPhone.setText("123455");
        etInputName.clearFocus();
        etInputPhone.clearFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_input_name:
                break;
            case R.id.et_input_phone:
                break;
            default:
                break;
        }
    }
}
