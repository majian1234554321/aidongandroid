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

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ConfirmOrderShopAdapter;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.SelectAddressActivity;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.EquipmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.NurturePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.EquipmentPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.NurturePresentImpl;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.ORDER_BUY_EQUIPMENT_IMMEDIATELY;
import static com.leyuan.aidong.utils.Constant.ORDER_BUY_NURTURE_IMMEDIATELY;
import static com.leyuan.aidong.utils.Constant.ORDER_FROM_CART;
import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEI_XIN;

/**
 * 确认订单
 * Created by song on 2016/9/23.
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener,
        CustomNestRadioGroup.OnCheckedChangeListener {
    private static final Double EXPRESS_PRICE = 15d;
    private static final int REQUEST_ADDRESS = 2;
    private static final int REQUEST_COUPON = 3;

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
    private List<String> days = new ArrayList<>();

    private String skuCode;
    private int amount;
    private String[] itemIds;
    private String integral;
    private String coin;
    private String coupon;
    private String payType;
    private String pickUpWay;           //取货方式(0-快递 1-自提)
    private String pickUpId;            //自提门店id或快递地址id
    private String pickUpDate;          //自提时间

    private int orderType;
    private double totalGoodsPrice;
    private boolean needExpress = false;

    private CartPresent cartPresent;
    private NurturePresent nurturePresent;
    private EquipmentPresent equipmentPresent;

    public static void start(Context context, ShopBean shop) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putExtra("shop",shop);
        context.startActivity(starter);
    }

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
        initVariable();
        initView();
        setListener();
    }

    private void initVariable(){
        payType = PAY_ALI;
        days = DateUtils.getSevenDate();
        pickUpDate = days.get(0);
        if(getIntent() == null) {
            return;
        }
        shopBeanList = getIntent().getParcelableArrayListExtra("selectedShops");
        if(shopBeanList == null || shopBeanList.isEmpty()){
            shopBeanList = new ArrayList<>();
            ShopBean shop = getIntent().getParcelableExtra("shop");
            shopBeanList.add(shop);
            pickUpId = shop.getPickUp().info.getId();
            pickUpWay = shop.getPickUp().getType();
            if(!shop.getItem().isEmpty()){
                GoodsBean goods = shop.getItem().get(0);
                skuCode = goods.getSkuCode();
                amount = FormatUtil.parseInt(goods.getAmount());
                totalGoodsPrice = FormatUtil.parseDouble(goods.getPrice())* amount;
                if(Constant.TYPE_NURTURE.equals(goods.getType())){
                    orderType = Constant.ORDER_BUY_NURTURE_IMMEDIATELY;
                }else {
                    orderType = Constant.ORDER_BUY_EQUIPMENT_IMMEDIATELY;
                }
            }
        }else {
            orderType = getIntent().getIntExtra("orderType",1);
            totalGoodsPrice = getIntent().getDoubleExtra("totalGoodsPrice", 0f);
        }
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
            if(Constant.DELIVERY_EXPRESS.equals(shopBean.getPickUp().getType())){
                needExpress = true;
                addressLayout.setVisibility(View.VISIBLE);
            }else {
                tvTime.setText(days.get(0));
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
        selfDeliveryLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar:
                finish();
                break;
            case R.id.ll_self_delivery:
                showDeliveryDateDialog();
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

    private void payOrder(){
        switch (orderType){
            case ORDER_FROM_CART:
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
                cartPresent.payCart(integral,coin,coupon,payType, pickUpId,
                        pickUpDate,payListener,itemIds);
                break;
            case ORDER_BUY_NURTURE_IMMEDIATELY:
                if(nurturePresent == null){
                    nurturePresent = new NurturePresentImpl(this);
                }
                nurturePresent.buyNurtureImmediately(skuCode,amount,coupon,integral,coin,payType,
                        String.valueOf(pickUpWay), pickUpId,pickUpDate,payListener);
                break;
            case ORDER_BUY_EQUIPMENT_IMMEDIATELY:
                if(equipmentPresent == null){
                    equipmentPresent = new EquipmentPresentImpl(this);
                }
                equipmentPresent.buyEquipmentImmediately(skuCode,amount,coupon,integral,coin,payType,
                        String.valueOf(pickUpWay), pickUpId,pickUpDate,payListener);
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            Toast.makeText(ConfirmOrderActivity.this,"支付成功啦",Toast.LENGTH_LONG).show();
            startActivity(new Intent(ConfirmOrderActivity.this,AppointSuccessActivity.class));
        }
    };

    private void showDeliveryDateDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_date)
                .items(days)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        pickUpDate = days.get(position);
                        tvTime.setText(pickUpDate);
                    }
                })
                .show();
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
                pickUpId = address.getAddressId();
            }
        }
    }

    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.cb_alipay:
                payType = PAY_ALI;
                break;
            case R.id.cb_weixin:
                payType = PAY_WEI_XIN;
                break;
            default:
                break;
        }
    }
}
