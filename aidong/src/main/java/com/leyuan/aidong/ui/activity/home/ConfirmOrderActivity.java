package com.leyuan.aidong.ui.activity.home;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.ConfirmOrderShopAdapter;
import com.leyuan.aidong.ui.activity.mine.CouponActivity;
import com.leyuan.aidong.ui.activity.mine.SelectAddressActivity;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.aidong.widget.customview.CustomNestRadioGroup;
import com.leyuan.aidong.widget.customview.ExtendTextView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单
 * Created by song on 2016/9/23.
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_ADDRESS = 1;
    private static final int REQUEST_COUPON = 2;
    private static final String ALI_PAY = "alipay";
    private static final String WEI_XIN_PAY = "wxpay";
    private static final String EXPRESS = "express";                //快递
    private static final String SELF_DELIVERY = "delivery";         //自提
    private static final String EXPRESS_AND_SELE_DELIVERY = "all";  //快递加自提

    private SimpleTitleBar titleBar;
    private NestedScrollView scrollView;

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
    private CustomNestRadioGroup radioGroup;
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;

    private ConfirmOrderShopAdapter shopAdapter;
    private List<ShopBean> shopBeanList = new ArrayList<>();

    private CartPresent cartPresent;

    private String[] itemIds;
    private String integral;
    private String coin;
    private String coupon;
    private String payType;
    private String pickUpId;

    private String totalPrice;
    private String takeType;    //收货方式

    public static void start(Context context, ArrayList<ShopBean> selectedShops) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putParcelableArrayListExtra("selectedShops",selectedShops);

        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        payType = ALI_PAY;
        cartPresent = new CartPresentImpl(this);
        if(getIntent() != null){
            shopBeanList = getIntent().getParcelableArrayListExtra("selectedShops");
        }

        initView();
        setListener();
    }

    private void initView() {
        scrollView= (NestedScrollView) findViewById(R.id.nested_scrollView);
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
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        shopAdapter = new ConfirmOrderShopAdapter(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.setAdapter(shopAdapter);
        shopAdapter.setData(shopBeanList);

        tvTotalPrice.setRightContent(totalPrice);
        tvPrice.setText(totalPrice);
    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        couponLayout.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar:
                finish();
                break;
            case R.id.rl_address:
                Intent  intent = new Intent(this,SelectAddressActivity.class);
                startActivityForResult(intent, REQUEST_ADDRESS);
                break;
            case R.id.ll_coupon:
                startActivity(new Intent(this, CouponActivity.class));
                break;
            case R.id.tv_pay:
                List<GoodsBean> goodsList = new ArrayList<>();
                for (ShopBean shopBean : shopBeanList) {
                    for (GoodsBean goodsBean : shopBean.getItem()) {
                        goodsList.add(goodsBean);
                    }
                }
                itemIds = new String[goodsList.size()];
                for (int i = 0; i < goodsList.size(); i++) {
                    itemIds[i] = goodsList.get(i).getId();
                }
                cartPresent.payCart(integral,coin,coupon,payType,pickUpId,payListener,itemIds);
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new PayInterface.PayListener() {
        @Override
        public void fail(String code, Object object) {
            String tip = "";
            switch (code){
                case "4000":
                    tip = "订单支付失败";
                    break;
                case "5000":
                    tip = "订单重复提交";
                    break;
                case "6001":
                    tip = "订单取消支付";
                    break;
                case "6002":
                    tip = "网络连接出错";
                    break;
                default:
                    break;
            }
            Toast.makeText(ConfirmOrderActivity.this,tip,Toast.LENGTH_LONG).show();
            Logger.w("AppointCourseActivity","failed:" + code + object.toString());
        }

        @Override
        public void success(String code, Object object) {
            Toast.makeText(ConfirmOrderActivity.this,"支付成功啦啦啦啦啦绿",Toast.LENGTH_LONG).show();
            startActivity(new Intent(ConfirmOrderActivity.this,AppointSuccessActivity.class));
            Logger.w("AppointCourseActivity","success:" + code + object.toString());
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            if(requestCode == REQUEST_ADDRESS){
                AddressBean address = data.getParcelableExtra("address");
                tvName.setText(address.getName());
                tvPhone.setText(address.getMobile());
                StringBuilder sb = new StringBuilder("收货地址: ");
                sb.append(address.getProvince()).append(address.getCity())
                        .append(address.getDistrict()).append(address.getAddress());
                tvAddress.setText(sb);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAnimations(){
        int cx = ScreenUtil.getScreenWidth(this);
        int cy = ScreenUtil.getScreenHeight(this);
        Animator anim = ViewAnimationUtils.createCircularReveal(titleBar, cx, cy, 0, cx);
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

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
