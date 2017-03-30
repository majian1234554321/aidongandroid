package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ConfirmOrderShopAdapter;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AddAddressActivity;
import com.leyuan.aidong.ui.mine.activity.CartActivity;
import com.leyuan.aidong.ui.mine.activity.SelectAddressActivity;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mine.activity.UpdateDeliveryInfoActivity;
import com.leyuan.aidong.ui.mvp.presenter.ConfirmOrderPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.ConfirmOrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ConfirmOrderActivityView;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.constant.CouponType;
import com.leyuan.aidong.utils.constant.DeliveryType;
import com.leyuan.aidong.utils.constant.GoodsType;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.utils.constant.SettlementType;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.EXPRESS_PRICE;
import static com.leyuan.aidong.utils.Constant.REQUEST_ADD_ADDRESS;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_ADDRESS;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_COUPON;
import static com.leyuan.aidong.utils.Constant.REQUEST_UPDATE_DELIVERY;


/**
 * 确认订单
 * Created by song on 2016/9/23.
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener,
        CustomNestRadioGroup.OnCheckedChangeListener ,ConfirmOrderActivityView, ConfirmOrderShopAdapter.DeliveryTypeListener {
    private SimpleTitleBar titleBar;
    private LinearLayout contentLayout;
    private SwitcherLayout switcherLayout;

    //收货或自提地址
    private RelativeLayout emptyAddressLayout;
    private RelativeLayout addressLayout;
    private ImageView ivDefault;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private LinearLayout selfDeliveryLayout;
    private TextView tvTime;

    //商品
    private RecyclerView rvGoods;

    //优惠劵与金币
    private TextView tvCoupon;
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
    private LinearLayout bottomLayout;
    private TextView tvTip;
    private TextView tvFinalPrice;
    private TextView tvPay;

    private List<String> days = new ArrayList<>();
    private List<ShopBean> shopBeanList = new ArrayList<>();
    private List<CouponBean> usableCoupons = new ArrayList<>();
    private ConfirmOrderShopAdapter shopAdapter;

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

    private String couponType;
    private String settlementType;
    private double totalGoodsPrice;
    private boolean needExpress = false;

    private ConfirmOrderPresent present;


    public static void start(Context context, ShopBean shop) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putExtra("shop",shop);
        context.startActivity(starter);
    }

    public static void start(Context context,ArrayList<ShopBean> selectedShops,double totalGoodsPrice) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putParcelableArrayListExtra("selectedShops",selectedShops);
        starter.putExtra("totalGoodsPrice",totalGoodsPrice);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        initVariable();
        initView();
        setListener();
        if(needExpress) {
            bottomLayout.setVisibility(View.GONE);
            present.getDefaultAddress(switcherLayout);
        }
        present.getSpecifyGoodsCoupon(couponType,itemIds);
    }

    private void initVariable(){
        payType = PayType.ALI;
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
            if(DeliveryType.EXPRESS.equals(pickUpWay)){
                pickUpDate = null;
            }
            if(!shop.getItem().isEmpty()){
                GoodsBean goods = shop.getItem().get(0);
                skuCode = goods.getSkuCode();
                amount = FormatUtil.parseInt(goods.getAmount());
                totalGoodsPrice = FormatUtil.parseDouble(goods.getPrice())* amount;
                if(GoodsType.NUTRITION.equals(goods.getType())){
                    couponType = CouponType.NUTRITION;
                    settlementType = SettlementType.NURTURE_IMMEDIATELY;
                }else {
                    couponType = CouponType.EQUIPMENT;
                    settlementType = SettlementType.EQUIPMENT_IMMEDIATELY;
                }
            }
        }else {
            couponType = CouponType.CART;
            settlementType  = SettlementType.CART;
            totalGoodsPrice = getIntent().getDoubleExtra("totalGoodsPrice", 0f);
        }

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

        present = new ConfirmOrderPresentImpl(this,this);
    }

    private void initView() {
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this, contentLayout);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        emptyAddressLayout = (RelativeLayout) findViewById(R.id.rl_empty_address);
        addressLayout = (RelativeLayout) findViewById(R.id.rl_address);
        ivDefault = (ImageView) findViewById(R.id.iv_default);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView)findViewById(R.id.tv_phone);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        selfDeliveryLayout = (LinearLayout) findViewById(R.id.ll_self_delivery);
        tvTime = (TextView) findViewById(R.id.tv_time);
        rvGoods = (RecyclerView) findViewById(R.id.rv_goods);
        tvCoupon = (TextView) findViewById(R.id.tv_coupon);
        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
        tvTotalGoodsPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.tv_coupon_price);
        tvDiscountPrice = (ExtendTextView) findViewById(R.id.tv_discount_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvFinalPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        shopAdapter = new ConfirmOrderShopAdapter(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.setAdapter(shopAdapter);
        shopAdapter.setData(shopBeanList);

        for (ShopBean shopBean : shopBeanList) {
            if(DeliveryType.EXPRESS.equals(shopBean.getPickUp().getType())){
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
        tvFinalPrice.setText(String.format(getString(R.string.rmb_price_double),
                needExpress ? totalGoodsPrice + EXPRESS_PRICE : totalGoodsPrice));
    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        emptyAddressLayout.setOnClickListener(this);
        tvCoupon.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        selfDeliveryLayout.setOnClickListener(this);
        shopAdapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_self_delivery:
                showDeliveryDateDialog();
                break;
            case R.id.rl_empty_address:
                startActivityForResult(new Intent(this,AddAddressActivity.class), REQUEST_ADD_ADDRESS);
                break;
            case R.id.rl_address:
                startActivityForResult(new Intent(this,SelectAddressActivity.class), REQUEST_SELECT_ADDRESS);
                break;
            case R.id.tv_coupon:
                if(usableCoupons != null && !usableCoupons.isEmpty()) {
                    startActivityForResult(new Intent(this, SelectCouponActivity.class)
                            .putParcelableArrayListExtra("couponList", (ArrayList<? extends Parcelable>)
                                    usableCoupons), REQUEST_SELECT_COUPON);
                }
                break;
            case R.id.tv_pay:
                payOrder();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDeliveryTypeClick(int position) {
        Intent intent = new Intent(this, UpdateDeliveryInfoActivity.class);
        intent.putExtra("shopBean",shopBeanList.get(position));
        startActivityForResult(intent,REQUEST_UPDATE_DELIVERY);
    }

    private void payOrder(){
        if(needExpress && TextUtils.isEmpty(pickUpId)){
            Toast.makeText(this,"请填写收货地址",Toast.LENGTH_LONG).show();
            return;
        }
        switch (settlementType){
            case SettlementType.CART:
                present.payCart(integral,coin,coupon,payType, pickUpId,
                        pickUpDate,payListener,itemIds);
                break;
            case SettlementType.NURTURE_IMMEDIATELY:
                present.buyNurtureImmediately(skuCode,amount,coupon,integral,coin,payType,
                        String.valueOf(pickUpWay), pickUpId,pickUpDate,payListener);
                break;
            case SettlementType.EQUIPMENT_IMMEDIATELY:
                present.buyEquipmentImmediately(skuCode,amount,coupon,integral,coin,payType,
                        String.valueOf(pickUpWay), pickUpId,pickUpDate,payListener);
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            Toast.makeText(ConfirmOrderActivity.this,"支付成功",Toast.LENGTH_LONG).show();
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
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.cb_alipay:
                payType = PayType.ALI;
                break;
            case R.id.cb_weixin:
                payType = PayType.WEIXIN;
                break;
            default:
                break;
        }
    }

    @Override
    public void setDefaultAddressResult(AddressBean address) {
        bottomLayout.setVisibility(View.VISIBLE);
        updateAddressStatus(address);
    }

    @Override
    public void setSpecifyGoodsCouponResult(List<CouponBean> usableCoupons) {
        this.usableCoupons = usableCoupons;
        if(usableCoupons == null || usableCoupons.isEmpty()){
            tvCoupon.setText("无可用");
            tvCoupon.setCompoundDrawables(null,null,null,null);
        }
    }

    @Override
    public void setRefreshCartDataResult(List<ShopBean> updatedList) {
        if(updatedList != null) {
            shopBeanList = updatedList;
            shopAdapter.setData(shopBeanList);

            Intent intent = new Intent(this, CartActivity.class);
            intent.putParcelableArrayListExtra("shopBeanList",
                    (ArrayList<? extends Parcelable>) shopBeanList);
            setResult(RESULT_OK,intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_SELECT_ADDRESS){
                AddressBean address = data.getParcelableExtra("address");
                setAddressInfo(address);
            }else if(requestCode == REQUEST_ADD_ADDRESS){
                AddressBean address = data.getParcelableExtra("address");
                updateAddressStatus(address);
            }else if(requestCode == REQUEST_SELECT_COUPON){
                CouponBean couponBean = data.getParcelableExtra("coupon");
                if(FormatUtil.parseInt(couponBean.getDiscount()) != 0) {
                    tvCoupon.setText(String.format(getString(R.string.rmb_minus_price_double),
                            FormatUtil.parseDouble(couponBean.getDiscount())));
                }else {
                    tvCoupon.setText("请选择");
                }
                tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getDiscount())));
                double deliveryPrice = needExpress ? EXPRESS_PRICE : 0;
                tvFinalPrice.setText(String.format(getString(R.string.rmb_price_double),
                        totalGoodsPrice + deliveryPrice - FormatUtil.parseDouble(couponBean.getDiscount())));
            }else if(requestCode == REQUEST_UPDATE_DELIVERY){
                present.refreshCartData();
            }
        }
    }

    private void updateAddressStatus(AddressBean address){
        if(address == null){
            addressLayout.setVisibility(View.GONE);
            emptyAddressLayout.setVisibility(View.VISIBLE);
        }else {
            setAddressInfo(address);
            addressLayout.setVisibility(View.VISIBLE);
            emptyAddressLayout.setVisibility(View.GONE);
        }
    }

    private void setAddressInfo(AddressBean address){
        ivDefault.setVisibility(address.isDefault() ? View.VISIBLE : View.GONE);
        tvName.setText(address.getName());
        tvPhone.setText(address.getMobile());
        StringBuilder sb = new StringBuilder("收货地址: ");
        sb.append(address.getProvince()).append(address.getCity())
                .append(address.getDistrict()).append(address.getAddress());
        tvAddress.setText(sb);
        pickUpId = address.getId();
    }

}
