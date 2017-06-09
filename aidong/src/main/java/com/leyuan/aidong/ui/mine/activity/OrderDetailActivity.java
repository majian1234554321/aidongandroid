package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.OrderDetailBean;
import com.leyuan.aidong.entity.ParcelBean;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.OrderPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.OrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.OrderDetailActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.QRCodeUtil;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;
import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;

/**
 * 订单详情
 * Created by song on 2016/9/1.
 */
public class OrderDetailActivity extends BaseActivity implements OrderDetailActivityView, View.OnClickListener,
        CustomNestRadioGroup.OnCheckedChangeListener, CountdownView.OnCountdownEndListener {
    private static final String UN_PAID = "pending";          //待付款
    private static final String PAID = "purchased";           //已支付
    private static final String FINISH = "confirmed";         //已确认
    private static final String CLOSE = "canceled";           //已关闭
    private long orderCountdownMill;

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
    private TextView tvExpressAddress;
    private TextView tvExpressTime;
    private ExtendTextView tvBuyer;   //快递收货人信息
    private ExtendTextView tvPhone;
    private ExtendTextView tvAddress;
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
    private ExtendTextView tvPayType;

    //支付信息
    private LinearLayout llPay;
    private RadioButton rbALiPay;
    private RadioButton rbWeiXinPay;
    private CustomNestRadioGroup payGroup;

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
    private ArrayList<GoodsBean> goodsList = new ArrayList<>();
    private OrderParcelAdapter expressAdapter;
    private OrderParcelAdapter selfDeliveryAdapter;
    private String orderId;
    private OrderPresent orderPresent;
    private String payType;
    private OrderDetailBean bean;

    public static void start(Context context, String id) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra("orderId", id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderCountdownMill = SystemInfoUtils.getOrderCountdown(this) * 60 * 1000;
        orderPresent = new OrderPresentImpl(this, this);
        if (getIntent() != null) {
            orderId = getIntent().getStringExtra("orderId");
        }

        initView();
        setListener();
        orderPresent.getOrderDetail(switcherLayout, orderId);
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this, llContent);
        tvState = (TextView) findViewById(R.id.tv_state);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_no);
        timeLayout = (LinearLayout) findViewById(R.id.ll_timer);
        timer = (CountdownView) findViewById(R.id.timer);
        expressInfoLayout = (LinearLayout) findViewById(R.id.ll_express_info);
        rlExpress = (RelativeLayout) findViewById(R.id.rl_express);
        tvExpressAddress = (TextView) findViewById(R.id.tv_express_address);
        tvExpressTime = (TextView) findViewById(R.id.tv_express_time);
        tvBuyer = (ExtendTextView) findViewById(R.id.tv_buyer);
        tvPhone = (ExtendTextView) findViewById(R.id.tv_phone);
        tvAddress = (ExtendTextView) findViewById(R.id.tv_address);
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
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);
        llPay = (LinearLayout) findViewById(R.id.ll_pay);
        payGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        rbALiPay = (RadioButton) findViewById(R.id.cb_alipay);
        rbWeiXinPay = (RadioButton) findViewById(R.id.cb_weixin);

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

    private void setListener() {
        titleBar.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvAfterSell.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvReBuy.setOnClickListener(this);
        payGroup.setOnCheckedChangeListener(this);
        timer.setOnCountdownEndListener(this);
        rlExpress.setOnClickListener(this);
    }

    @Override
    public void setOrderDetail(OrderDetailBean bean) {
        this.bean = bean;
        int goodsCount = 0;
        int returnCount = 0;
        bottomLayout.setVisibility(View.VISIBLE);

        payType = bean.getPayType();
        if (PAY_ALI.equals(payType)) {
            rbALiPay.setChecked(true);
        } else {
            rbWeiXinPay.setChecked(true);
        }

        tvOrderNo.setText(String.format(getString(R.string.order_no), bean.getId()));
        timeLayout.setVisibility(UN_PAID.equals(bean.getStatus()) ? View.VISIBLE : View.GONE);
        timer.start(DateUtils.getCountdown(bean.getCreatedAt(), orderCountdownMill));

        for (ParcelBean parcelBean : bean.getParcel()) {
            if (DELIVERY_EXPRESS.equals(parcelBean.getPickUpWay())) {
                expressList.add(parcelBean);
            } else {
                selfDeliveryList.add(parcelBean);
            }
            for (GoodsBean goodsBean : parcelBean.getItem()) {
                goodsCount += FormatUtil.parseInt(goodsBean.getAmount());
                goodsList.add(goodsBean);
                returnCount += goodsBean.getCan_return();
            }
        }

        if (expressList != null && !expressList.isEmpty()) {
            expressAdapter.setData(expressList);
            expressInfoLayout.setVisibility(View.VISIBLE);
            ParcelBean expressParcel = expressList.get(0);
            tvBuyer.setRightContent(expressParcel.getName());
            tvPhone.setRightContent(expressParcel.getMobile());
            tvAddress.setRightContent(expressParcel.getAddress());
            tvRemarks.setRightContent(expressParcel.getRemark());
            if(PAID.equals(bean.getStatus()) || FINISH.equals(bean.getStatus())){
                orderPresent.getOrderDetailExpressInfo(orderId);
                rlExpress.setVisibility(View.VISIBLE);
            } else {
                rlExpress.setVisibility(View.GONE);
            }
        } else {
            expressInfoLayout.setVisibility(View.GONE);
        }

        if (selfDeliveryList != null && !selfDeliveryList.isEmpty()) {
            selfDeliveryAdapter.setData(selfDeliveryList);
            selfDeliveryInfoLayout.setVisibility(View.VISIBLE);
            tvDeliveryTime.setRightContent(selfDeliveryList.get(0).getPickUpDate());
            if (UN_PAID.equals(bean.getStatus()) || CLOSE.equals(bean.getStatus())) {
                rlQrCode.setVisibility(View.GONE);
            } else {
                rlQrCode.setVisibility(View.VISIBLE);
                if (PAID.equals(bean.getStatus())) {
                    tvQrNum.setText(bean.getId());
                    ivQr.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFF000000, bean.getId(),
                            DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                } else {
                    tvQrNum.setText(bean.getId());
                    tvQrNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tvQrNum.setTextColor(Color.parseColor("#ebebeb"));
                    ivQr.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getId(),
                            DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                }
            }
        } else {
            selfDeliveryInfoLayout.setVisibility(View.GONE);
        }

        //订单信息
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getTotal())));
        tvExpressPrice.setRightContent(expressList != null && !expressList.isEmpty()
                ? String.format(getString(R.string.rmb_price_double), SystemInfoUtils.getExpressPrice(this)) : "¥ 0.00");
        tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getCoupon())));
        tvAiBi.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getCoin())));
        tvAiDou.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getIntegral())));
        tvStartTime.setRightContent(bean.getCreatedAt());
        tvPayType.setVisibility(UN_PAID.equals(bean.getStatus()) ? View.GONE : View.VISIBLE);
        tvPayType.setRightContent(PAY_ALI.equals(bean.getPayType()) ? "支付宝" : "微信");
        tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPayAmount())));

        llPay.setVisibility(UN_PAID.equals(bean.getStatus()) ? View.VISIBLE : View.GONE);
        tvGoodsCount.setText(getString(R.string.goods_count, goodsCount));

        tvCancel.setVisibility(UN_PAID.equals(bean.getStatus()) ? View.VISIBLE : View.GONE);
        tvPay.setVisibility(UN_PAID.equals(bean.getStatus()) ? View.VISIBLE : View.GONE);
        tvAfterSell.setVisibility(PAID.equals(bean.getStatus()) || FINISH.equals(bean.getStatus())
                ? View.VISIBLE : View.GONE);
        if (returnCount == 0) {
            tvAfterSell.setVisibility(View.GONE);
        }
        tvConfirm.setVisibility(PAID.equals(bean.getStatus()) ? View.VISIBLE : View.GONE);
        tvReBuy.setVisibility(FINISH.equals(bean.getStatus()) || CLOSE.equals(bean.getStatus())
                ? View.VISIBLE : View.GONE);

        tvState.setText(bean.getStatus().equals(UN_PAID) ? getString(R.string.un_paid)
                : bean.getStatus().equals(PAID) ? getString(R.string.paid)
                : bean.getStatus().equals(FINISH) ? getString(R.string.order_finish)
                : getString(R.string.order_close));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cancel_order:
                orderPresent.cancelOrder(orderId);
                break;
            case R.id.tv_pay:
                PayInterface payInterface = PAY_ALI.equals(payType) ?
                        new AliPay(this, payListener) : new WeiXinPay(this, payListener);
                payInterface.payOrder(bean.getPay_option());
                break;
            case R.id.tv_after_sell:
                ApplyServiceActivity.start(this, orderId, goodsList);
                finish();
                break;
            case R.id.tv_confirm:
                orderPresent.confirmOrder(orderId);
                break;
            case R.id.tv_delete:
                orderPresent.deleteOrder(orderId);
                break;
            case R.id.tv_rebuy:
                orderPresent.reBuyOrder(orderId);
                break;
            case R.id.rl_express:
                ExpressInfoActivity.start(this, orderId);
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            ToastGlobal.showLong("支付成功");
            PaySuccessActivity.start(OrderDetailActivity.this, orderPresent.getShareInfo());
        }

        @Override
        public void onFree() {
            PaySuccessActivity.start(OrderDetailActivity.this, orderPresent.getShareInfo());
//            startActivity(new Intent(OrderDetailActivity.this,PaySuccessActivity.class));
        }
    };

    @Override
    public void cancelOrderResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            clearList();
            orderPresent.getOrderDetail(orderId);
            ToastGlobal.showLong("取消成功");
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void confirmOrderResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            clearList();
            orderPresent.getOrderDetail(orderId);
            ToastGlobal.showLong("确认成功");
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void deleteOrderResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            orderPresent.getOrderDetail(orderId);
            ToastGlobal.showLong("删除成功");
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void reBuyOrderResult(List<String> cartIds) {
        CartActivity.start(this, cartIds);
    }


    @Override
    public void getExpressInfoResult(String status, String time) {
        tvExpressAddress.setText(status);
        tvExpressTime.setText(time);
    }

    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.cb_alipay:
                payType = PAY_ALI;
                break;
            case R.id.cb_weixin:
                payType = PAY_WEIXIN;
                break;
            default:
                break;
        }
    }

    @Override
    public void onEnd(CountdownView cv) {
        bean.setStatus(CLOSE);
        setOrderDetail(bean);
    }

    private void clearList(){
        goodsList.clear();
        expressList.clear();
        selfDeliveryList.clear();
    }
}
