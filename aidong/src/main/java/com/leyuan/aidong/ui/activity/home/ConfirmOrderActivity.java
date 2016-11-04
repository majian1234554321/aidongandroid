package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.AddressActivity;
import com.leyuan.aidong.ui.activity.mine.CouponActivity;
import com.leyuan.aidong.ui.activity.mine.PaySuccessActivity;
import com.leyuan.aidong.widget.customview.ExtendTextView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

/**
 * 确认订单
 * Created by song on 2016/9/23.
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {
    private SimpleTitleBar titleBar;

    //收货地址
    private RelativeLayout addressLayout;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;

    //商品
    private RecyclerView rvGoods;

    //优惠劵与金币
    private LinearLayout couponLayout;
    private LinearLayout goldLayout;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvExpressPrice;
    private ExtendTextView tvCouponPrice;
    private ExtendTextView tvDiscountPrice;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;

    //支付方式及支付状态
    private RadioButton cbAlipay;
    private RadioButton cbWeixin;
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;


    public static void start(Context context) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        initView();
        setListener();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        addressLayout = (RelativeLayout) findViewById(R.id.rl_address);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView)findViewById(R.id.tv_phone);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        rvGoods = (RecyclerView) findViewById(R.id.rv_goods);
        couponLayout = (LinearLayout) findViewById(R.id.ll_coupon);
        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
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
        titleBar.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        couponLayout.setOnClickListener(this);
        tvPay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar:
                finish();
                break;
            case R.id.rl_address:
                startActivity(new Intent(this, AddressActivity.class));
                break;
            case R.id.ll_coupon:
                startActivity(new Intent(this, CouponActivity.class));
                break;
            case R.id.tv_pay:
                startActivity(new Intent(this, PaySuccessActivity.class));
                break;
            default:
                break;
        }
    }
}
