package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.widget.customview.ExtendTextView;

/**
 * 订单详情
 * Created by song on 2016/9/1.
 */
public class OrderDetailActivity extends BaseActivity{

    //订单状态
    private TextView tvState;
    private TextView tvTimeOrNum;
    private RelativeLayout rlExpress;
    private SimpleDraweeView dvExpress;
    private TextView tvExpressAddress;
    private TextView tvExpressTime;
    private LinearLayout llCode;
    private TextView tvCodeNum;
    private SimpleDraweeView dvCode;

    //收货信息
    private ExtendTextView tvBuyer;
    private ExtendTextView tvPhone;
    private ExtendTextView tvAddress;
    private ExtendTextView tvInvoice;
    private ExtendTextView tvRemarks;
    private RecyclerView rvGoods;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvExpressPrice;
    private ExtendTextView couponPrice;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;
    private ExtendTextView tvStartTime;
    private ExtendTextView tvPayTime;
    private ExtendTextView tvPayType;

    //支付方式
    private LinearLayout payLayout;
    private CheckBox cbAliPay;
    private CheckBox cbWeixinPay;

    //底部操作按钮及商品总信息
    private TextView tvGoodsCount;
    private TextView tvPrice;
    private TextView tvLeftButton;
    private TextView tvRightButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        tvState = (TextView) findViewById(R.id.tv_state);
        tvTimeOrNum = (TextView) findViewById(R.id.tv_time_or_num);
        rlExpress = (RelativeLayout) findViewById(R.id.rl_express);
        dvExpress = (SimpleDraweeView) findViewById(R.id.dv_express);
        tvExpressAddress = (TextView) findViewById(R.id.tv_express_address);
        tvExpressTime = (TextView) findViewById(R.id.tv_express_time);
        llCode = (LinearLayout) findViewById(R.id.ll_code);
        tvCodeNum = (TextView) findViewById(R.id.tv_code_num);
        dvCode = (SimpleDraweeView) findViewById(R.id.dv_code);

        tvBuyer = (ExtendTextView) findViewById(R.id.tv_buyer);
        tvPhone = (ExtendTextView) findViewById(R.id.tv_phone);
        tvAddress = (ExtendTextView) findViewById(R.id.tv_address);
        tvInvoice = (ExtendTextView) findViewById(R.id.tv_invoice);
        tvRemarks = (ExtendTextView) findViewById(R.id.tv_remarks);
        rvGoods = (RecyclerView) findViewById(R.id.rv_goods);

        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        couponPrice = (ExtendTextView) findViewById(R.id.coupon_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayTime = (ExtendTextView) findViewById(R.id.tv_pay_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);

        payLayout = (LinearLayout)findViewById(R.id.ll_pay);
        cbAliPay = (CheckBox)findViewById(R.id.cb_alipay);
        cbWeixinPay = (CheckBox)findViewById(R.id.cb_weixin);

        tvGoodsCount = (TextView) findViewById(R.id.tv_goods_count);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvLeftButton = (TextView) findViewById(R.id.tv_left_button);
        tvRightButton = (TextView) findViewById(R.id.tv_right_button);
    }
}
