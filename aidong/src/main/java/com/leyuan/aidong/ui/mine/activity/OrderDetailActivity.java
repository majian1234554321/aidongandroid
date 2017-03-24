package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.OrderParcelAdapter;
import com.leyuan.aidong.entity.OrderDetailBean;
import com.leyuan.aidong.entity.ParcelBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.OrderPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.OrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.OrderDetailActivityView;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.QRCodeUtil;
import com.leyuan.aidong.utils.constant.DeliveryType;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

import static com.leyuan.aidong.ui.App.context;
import static com.leyuan.aidong.utils.Constant.EXPRESS_PRICE;

/**
 * 订单详情
 * Created by song on 2016/9/1.
 */
public class OrderDetailActivity extends BaseActivity implements OrderDetailActivityView, View.OnClickListener {
    private static final String UN_PAID = "pending";          //待付款
    private static final String PAID = "purchased";    //已支付
    private static final String FINISH = "confirmed";         //已确认
    private static final String CLOSE = "canceled";           //已关闭

    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private LinearLayout llContent;
    private TextView tvState;
    private TextView tvOrderNo;
    private LinearLayout timeLayout;
    private CountdownView timer;

    //快递信息
    private LinearLayout expressInfoLayout;
    private RelativeLayout rlExpress;  //快递详情
    private ImageView ivExpress;
    private TextView tvExpressAddress;
    private TextView tvExpressTime;
    private ExtendTextView tvBuyer;   //快递收货人信息
    private ExtendTextView tvPhone;
    private ExtendTextView tvAddress;
    private ExtendTextView tvInvoice;
    private ExtendTextView tvRemarks;
    private RecyclerView expressGoodsRecyclerView; //快递商品信息

    //自提信息
    private LinearLayout selfDeliveryInfoLayout;
    private RelativeLayout rlQrCode;   //自提条形码
    private TextView tvQrNum;
    private ImageView ivQr;
    private ExtendTextView tvDeliveryTime; //自提时间
    private RecyclerView selfDeliveryGoodsRecyclerView; //自提商品信息

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvExpressPrice;
    private ExtendTextView tvCouponPrice;
    private ExtendTextView tvAiBi;
    private ExtendTextView tvAiDou;
    private ExtendTextView tvStartTime;
    private ExtendTextView tvPayTime;
    private ExtendTextView tvPayType;

    //支付信息
    private LinearLayout llPay;
    private RadioButton cbAlipay;
    private RadioButton cbWeixin;

    //底部状态按钮信息
    private LinearLayout bottomLayout;
    private TextView tvGoodsCount;
    private TextView tvPayTip;
    private TextView tvPrice;
    private TextView tvCancel;
    private TextView tvPay;
    private TextView tvAfterSell;
    private TextView tvConfirm;
    private TextView tvDelete;
    private TextView tvReBuy;

    private List<ParcelBean> expressList = new ArrayList<>();
    private List<ParcelBean> selfDeliveryList = new ArrayList<>();
    private OrderParcelAdapter expressAdapter;
    private OrderParcelAdapter selfDeliveryAdapter;
    private String orderId;
    private OrderPresent orderPresent;


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
        setListener();
        orderPresent.getOrderDetail(switcherLayout,orderId);
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,llContent);
        tvState = (TextView) findViewById(R.id.tv_state);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_no);
        timeLayout = (LinearLayout) findViewById(R.id.ll_timer);
        timer = (CountdownView) findViewById(R.id.timer);
        expressInfoLayout = (LinearLayout) findViewById(R.id.ll_express_info);
        rlExpress = (RelativeLayout) findViewById(R.id.rl_express);
        ivExpress = (ImageView) findViewById(R.id.dv_express);
        tvExpressAddress = (TextView) findViewById(R.id.tv_express_address);
        tvExpressTime = (TextView) findViewById(R.id.tv_express_time);
        tvBuyer = (ExtendTextView) findViewById(R.id.tv_buyer);
        tvPhone = (ExtendTextView) findViewById(R.id.tv_phone);
        tvAddress = (ExtendTextView) findViewById(R.id.tv_address);
        tvInvoice = (ExtendTextView) findViewById(R.id.tv_invoice);
        tvRemarks = (ExtendTextView) findViewById(R.id.tv_remarks);
        expressGoodsRecyclerView = (RecyclerView) findViewById(R.id.rv_express_goods);
        selfDeliveryInfoLayout = (LinearLayout) findViewById(R.id.ll_self_delivery_info);
        rlQrCode = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvQrNum = (TextView) findViewById(R.id.tv_qr_num);
        ivQr = (ImageView) findViewById(R.id.iv_qr);
        tvDeliveryTime = (ExtendTextView) findViewById(R.id.tv_delivery_time);
        selfDeliveryGoodsRecyclerView = (RecyclerView) findViewById(R.id.rv_self_delivery_goods);
        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.coupon_price);
        tvAiBi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAiDou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayTime = (ExtendTextView) findViewById(R.id.tv_pay_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);
        llPay = (LinearLayout) findViewById(R.id.ll_pay);
        cbAlipay = (RadioButton) findViewById(R.id.cb_alipay);
        cbWeixin = (RadioButton) findViewById(R.id.cb_weixin);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        tvGoodsCount = (TextView) findViewById(R.id.tv_goods_count);
        tvPayTip = (TextView) findViewById(R.id.tv_pay_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCancel = (TextView) findViewById(R.id.tv_cancel_order);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvAfterSell = (TextView) findViewById(R.id.tv_after_sell);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvReBuy = (TextView) findViewById(R.id.tv_rebuy);

        expressAdapter = new OrderParcelAdapter(this);
        expressGoodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expressGoodsRecyclerView.setAdapter(expressAdapter);
        selfDeliveryAdapter = new OrderParcelAdapter(this);
        selfDeliveryGoodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selfDeliveryGoodsRecyclerView.setAdapter(selfDeliveryAdapter);
    }

    private void setListener(){
        titleBar.setOnClickListener(this);
    }

    @Override
    public void setOrderDetail(OrderDetailBean bean) {
        bottomLayout.setVisibility(View.VISIBLE);

        tvOrderNo.setText(String.format(getString(R.string.order_no),bean.getId()));
        tvOrderNo.setVisibility(UN_PAID.equals(bean.getStatus()) ? View.GONE : View.VISIBLE);
        timeLayout.setVisibility(UN_PAID.equals(bean.getStatus()) ? View.VISIBLE : View.GONE);

        for (ParcelBean parcelBean : bean.getParcel()) {
            if(DeliveryType.EXPRESS.equals(parcelBean.getPickUpWay())){
                expressList.add(parcelBean);
            }else {
                selfDeliveryList.add(parcelBean);
            }
        }

        if(expressList != null && !expressList.isEmpty()) {
            expressAdapter.setData(expressList);
            expressInfoLayout.setVisibility(View.VISIBLE);
            ParcelBean expressParcel = expressList.get(0);
            tvBuyer.setRightContent(expressParcel.getName());
            tvPhone.setRightContent(expressParcel.getMobile());
            tvAddress.setRightContent(expressParcel.getAddress());
            tvRemarks.setRightContent(expressParcel.getRemark());

        }else {
            expressInfoLayout.setVisibility(View.GONE);
        }

        if(selfDeliveryList != null && !selfDeliveryList.isEmpty()) {
            selfDeliveryAdapter.setData(selfDeliveryList);
            selfDeliveryInfoLayout.setVisibility(View.VISIBLE);
            tvDeliveryTime.setRightContent(selfDeliveryList.get(0).getPickUpDate());
            tvQrNum.setText(bean.getId());
            ivQr.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFF000000, bean.getId(),
                    DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
        }else {
            selfDeliveryInfoLayout.setVisibility(View.GONE);
        }

        setOrderInfo(bean);

        tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPayAmount())));

        //与订单状态有关:订单状态 支付方式 支付状态按钮及商品总信息
        switch (bean.getStatus()){
            case UN_PAID:
                tvState.setText(context.getString(R.string.un_paid));
                tvCancel.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.VISIBLE);
                break;
            case PAID:
                tvState.setText(context.getString(R.string.paid));
                break;
            case FINISH:
                tvState.setText(context.getString(R.string.order_finish));
                tvAfterSell.setVisibility(View.VISIBLE);
                tvReBuy.setVisibility(View.VISIBLE);
                break;
            case CLOSE:
                tvState.setText(context.getString(R.string.order_close));
                tvReBuy.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    private void setOrderInfo(OrderDetailBean bean){
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getTotal())));
        tvExpressPrice.setRightContent(expressList!= null && !expressList.isEmpty()
                ? String.format(getString(R.string.rmb_price_double),EXPRESS_PRICE):"¥ 0.00");
        tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getCoupon())));
        tvAiBi.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getCoin())));
        tvAiDou.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getIntegral())));
        tvStartTime.setRightContent(bean.getCreatedAt());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
