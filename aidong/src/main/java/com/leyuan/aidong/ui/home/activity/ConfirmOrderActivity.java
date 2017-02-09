package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.leyuan.aidong.adapter.home.ConfirmOrderShopAdapter;
import com.leyuan.aidong.ui.mine.activity.SelectAddressActivity;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.NurturePresentImpl;
import com.leyuan.aidong.utils.Logger;
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
    public static final int ORDER_CART= 1;
    public static final int ORDER_BUG_NURTURE_IMMEDIATELY = 2;
    public static final int ORDER_BUY_EQUIPMENT_IMMEDIATELY = 3;
    private static final String ALI_PAY = "alipay";
    private static final String WEI_XIN_PAY = "wxpay";
    private static final Double EXPRESS_PRICE = 15d;
    private static final int REQUEST_ADDRESS = 1;
    private static final int REQUEST_COUPON = 2;

    private SimpleTitleBar titleBar;
    private NestedScrollView scrollView;

    //收货或自提地址
    private RelativeLayout addressLayout;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private LinearLayout selfDeliveryLayout;
    private TextView tvTime;

    //商品
    private RecyclerView rvGoods;

    //优惠劵与金币
    private LinearLayout couponLayout;
    private LinearLayout goldLayout;

    //订单信息
    private ExtendTextView tvTotalGoodsPrice;
    private ExtendTextView tvExpressPrice;
    private ExtendTextView tvCouponPrice;
    private ExtendTextView tvDiscountPrice;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;

    //支付方式及支付状态
    private CustomNestRadioGroup radioGroup;
    private TextView tvTip;
    private TextView tvTruePrice;
    private TextView tvPay;

    private ConfirmOrderShopAdapter shopAdapter;
    private List<ShopBean> shopBeanList = new ArrayList<>();

    private String[] itemIds;
    private String integral;
    private String coin;
    private String coupon;
    private String payType;
    private String expressAddress;

    private int orderType;
    private double totalGoodsPrice;
    private boolean needExpress = false;

    private CartPresent cartPresent;
    private NurturePresent nurturePresent;
    private EquipmentPresent equipmentPresent;

    public static void start(Context context, int orderType,ArrayList<ShopBean> selectedShops,
                             double totalGoodsPrice) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putParcelableArrayListExtra("selectedShops",selectedShops);
        starter.putExtra("totalGoodsPrice",totalGoodsPrice);
        starter.putExtra("orderType",orderType);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        payType = ALI_PAY;
        if(getIntent() != null){
            orderType = getIntent().getIntExtra("orderType",1);
            shopBeanList = getIntent().getParcelableArrayListExtra("selectedShops");
            totalGoodsPrice = getIntent().getDoubleExtra("totalGoodsPrice",0f);
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
        selfDeliveryLayout = (LinearLayout) findViewById(R.id.ll_self_delivery);
        tvTime = (TextView) findViewById(R.id.tv_time);
        rvGoods = (RecyclerView) findViewById(R.id.rv_goods);
        couponLayout = (LinearLayout) findViewById(R.id.ll_coupon);
        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
        tvTotalGoodsPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.tv_coupon_price);
        tvDiscountPrice = (ExtendTextView) findViewById(R.id.tv_discount_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvTruePrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        shopAdapter = new ConfirmOrderShopAdapter(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.setAdapter(shopAdapter);
        shopAdapter.setData(shopBeanList);

        for (ShopBean shopBean : shopBeanList) {
            if(shopBean.getName().equals("仓库发货")){
                needExpress = true;
                addressLayout.setVisibility(View.VISIBLE);
            }else {
                selfDeliveryLayout.setVisibility(View.VISIBLE);
            }
        }

        tvTotalGoodsPrice.setRightContent(
                String.format(getString(R.string.rmb_price_double),totalGoodsPrice));
        tvExpressPrice.setRightContent(
                needExpress ? String.format(getString(R.string.rmb_price_double),EXPRESS_PRICE):"¥ 0.00");
        tvTruePrice.setText(String.format(getString(R.string.rmb_price_double),
                needExpress ? totalGoodsPrice + EXPRESS_PRICE : totalGoodsPrice));
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
                startActivity(new Intent(this, SelectCouponActivity.class));
                break;
            case R.id.tv_pay:
                payOrder();
                break;
            default:
                break;
        }
    }

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

    private void payOrder(){
        switch (orderType){
            case ORDER_CART:
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
                if(cartPresent == null){
                    cartPresent = new CartPresentImpl(this);
                }
                cartPresent.payCart(integral,coin,coupon,payType,expressAddress,payListener,itemIds);
                break;
            case ORDER_BUG_NURTURE_IMMEDIATELY:
                if(nurturePresent == null){
                    nurturePresent = new NurturePresentImpl(this);
                }
                //nurturePresent.buyNurtureImmediately();
                break;
            case ORDER_BUY_EQUIPMENT_IMMEDIATELY:

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
            Toast.makeText(ConfirmOrderActivity.this,"支付成功",Toast.LENGTH_LONG).show();
            startActivity(new Intent(ConfirmOrderActivity.this,AppointSuccessActivity.class));
            Logger.w("AppointCourseActivity","success:" + code + object.toString());
        }
    };
}
