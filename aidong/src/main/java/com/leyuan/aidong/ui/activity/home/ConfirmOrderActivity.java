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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.ConfirmOrderShopAdapter;
import com.leyuan.aidong.ui.activity.mine.CouponActivity;
import com.leyuan.aidong.ui.activity.mine.PaySuccessActivity;
import com.leyuan.aidong.ui.activity.mine.SelectAddressActivity;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.aidong.widget.customview.ExtendTextView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单
 * Created by song on 2016/9/23.
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUST_ADDRESS = 1;
    private static final int REQUST_COUPON = 2;
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
    private RadioButton cbAlipay;
    private RadioButton cbWeixin;
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;

    private ConfirmOrderShopAdapter shopAdapter;
    private List<ShopBean> shopBeanList = new ArrayList<>();

    public static void start(Context context, ArrayList<ShopBean> selectedShops) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putParcelableArrayListExtra("selectedShops",selectedShops);
        context.startActivity(starter);
    }

    public static void start(Context context,ShopBean selectedShop) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putExtra("selectedShop",selectedShop);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        if(getIntent() != null){
            if(getIntent().getParcelableArrayListExtra("selectedShops") != null) {
                shopBeanList = getIntent().getParcelableArrayListExtra("selectedShops");
            }
            ShopBean selectedShop = getIntent().getParcelableExtra("selectedShop");
            if(null != selectedShop){
                shopBeanList.add(selectedShop);
            }
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
        cbAlipay = (RadioButton) findViewById(R.id.cb_alipay);
        cbWeixin = (RadioButton) findViewById(R.id.cb_weixin);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        shopAdapter = new ConfirmOrderShopAdapter(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.setAdapter(shopAdapter);
        shopAdapter.setData(shopBeanList);
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
                Intent  intent = new Intent(this,SelectAddressActivity.class);
                startActivityForResult(intent,REQUST_ADDRESS);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            if(requestCode == REQUST_ADDRESS){
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
}
