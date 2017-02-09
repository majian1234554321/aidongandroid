package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.OrderDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.adapter.CartShopAdapter;
import com.leyuan.aidong.ui.mvp.presenter.OrderPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.OrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.OrderDetailActivityView;
import com.leyuan.aidong.widget.customview.ExtendTextView;

/**
 * 订单详情
 * Created by song on 2016/9/1.
 */
public class OrderDetailActivity extends BaseActivity implements OrderDetailActivityView{
    private static final String UN_PAID = "0";          //待付款
    private static final String UN_DELIVERY = "1";      //待发货
    private static final String DELIVERED = "2";        //已发货
    private static final String FINISH = "3";           //已完成
    private static final String CLOSE = "4";            //已关闭
    private static final String UN_SELF_DELIVERY = "5"; //待自提
    private static final String SELF_DELIVERED = "6";   //已自提

    //切换加载中 无内容,无网络控件
   // private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;

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
    private RecyclerView goodsRecyclerView;
    private CartShopAdapter cartShopAdapter;

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
    private RadioButton cbAliPay;
    private RadioButton cbWeixinPay;

    //支付状态按钮及商品总信息
    private TextView tvGoodsCount;
    private TextView tvPayTip;
    private TextView tvPrice;
    private TextView tvCancel;
    private TextView tvPay;
    private TextView tvExpress;
    private TextView tvReceiving;
    private TextView tvDelete;
    private TextView tvAgainBuy;

    //Present层对象
    private OrderPresent orderPresent;
    private String orderId;

    public static void start(Context context,String id) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra("orderId",id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderPresent = new OrderPresentImpl(this,this);
        if(getIntent() != null){
            orderId = getIntent().getStringExtra("orderId");
        }

        initView();
      //  orderPresent.getOrderDetail(orderId,switcherLayout);
    }

    private void initView(){
        contentLayout = (LinearLayout)findViewById(R.id.ll_content);
     //   switcherLayout = new SwitcherLayout(this,contentLayout);

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
        goodsRecyclerView = (RecyclerView) findViewById(R.id.rv_goods);
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartShopAdapter = new CartShopAdapter(this);
        goodsRecyclerView.setAdapter(cartShopAdapter);

        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        couponPrice = (ExtendTextView) findViewById(R.id.coupon_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayTime = (ExtendTextView) findViewById(R.id.tv_pay_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);

        payLayout = (LinearLayout)findViewById(R.id.ll_pay);
        cbAliPay = (RadioButton)findViewById(R.id.cb_alipay);
        cbWeixinPay = (RadioButton)findViewById(R.id.cb_weixin);

        tvGoodsCount = (TextView) findViewById(R.id.tv_goods_count);
        tvPayTip = (TextView) findViewById(R.id.tv_pay_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvExpress = (TextView) findViewById(R.id.tv_express);
        tvReceiving = (TextView) findViewById(R.id.tv_receiving);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvAgainBuy = (TextView) findViewById(R.id.tv_again_buy);
    }

    @Override
    public void setOrderDetail(OrderDetailBean orderDetailBean) {
        //与订单状态无关: 收货信息 订单信息
        //tvBuyer.setRightContent(orderDetailBean.);

        //与订单状态有关:订单状态 支付方式 支付状态按钮及商品总信息
        switch (orderDetailBean.getStatus()){
            case UN_PAID:

                break;
            case UN_DELIVERY:

                break;
            case DELIVERED:

                break;
            case FINISH:

                break;
            case CLOSE:

                break;
            case UN_SELF_DELIVERY:

                break;
            case SELF_DELIVERED:

                break;
            default:
                break;
        }
    }
}
