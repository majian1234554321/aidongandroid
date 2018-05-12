package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.OptionsPickerView;
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
import com.leyuan.aidong.ui.mine.activity.AppointmentMineActivityNew;
import com.leyuan.aidong.ui.mine.activity.OrderActivity;
import com.leyuan.aidong.ui.mine.activity.PaySuccessActivity;
import com.leyuan.aidong.ui.mine.activity.SelectAddressActivity;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mine.activity.UpdateDeliveryInfoActivity;
import com.leyuan.aidong.ui.mvp.presenter.ConfirmOrderPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.ConfirmOrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ConfirmOrderActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.constant.DeliveryType;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.utils.constant.SettlementType;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leyuan.aidong.R.id.ll__receiving_time;
import static com.leyuan.aidong.R.id.txt_receving_time;
import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;
import static com.leyuan.aidong.utils.Constant.DELIVERY_SELF;
import static com.leyuan.aidong.utils.Constant.GOODS_FOODS;
import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_ADD_ADDRESS;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_ADDRESS;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_COUPON;
import static com.leyuan.aidong.utils.Constant.REQUEST_UPDATE_DELIVERY;


/**
 * 确认订单:从商品立即购买跳转过来
 * Created by song on 2016/9/23.
 */
public class ConfirmOrderGoodsActivity extends BaseActivity implements View.OnClickListener,
        CustomNestRadioGroup.OnCheckedChangeListener, ConfirmOrderActivityView, ConfirmOrderShopAdapter.DeliveryTypeListener {
    private static final String TAG = "ConfirmOrderActivity";
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
    private TextView tvTime, txt_self_delivery_identify, txt_receiving_left_identify;

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
    private List<String> limit_days = new ArrayList<>();
    private List<ShopBean> shopBeanList = new ArrayList<>();
    private List<CouponBean> usableCoupons = new ArrayList<>();
    private ConfirmOrderShopAdapter shopAdapter;

    private String integral;
    private String coin;
    private String couponPrice;
    private String couponId;

    private String skuCode;
    private int amount;
    @DeliveryType
    private String pickUpWay;           //取货方式(0-快递 1-自提)
    @PayType
    private String payType;
    private String pickUpId;                          //快递地址或自提场馆id(立即购买时)
    private String addressId;                         //购物车结算时快递地址id
    private String pickUpDate;                        //自提时间
    private ArrayList<String> itemFromIdAmount = new ArrayList<>();
    private ArrayList<String> pickUpIds = new ArrayList<>();
    private Map<String, String> goodsIdGymID = new HashMap();

    @SettlementType
    private String settlementType;
    private double totalGoodsPrice;

    private boolean needExpress = false;              //是否需要快递
    private boolean needSelfDelivery = false;         //是否需要自提
    private Double expressPrice = 15d;

    private ConfirmOrderPresent present;
    private TextView txtrecevingtime;
    private LinearLayout llReceivingTime;
    private List<String> receivingTimeQuantum;
    private String pick_up_period;
    private String selectedUserCouponId;
    private String currentGoodsID;
    private boolean is_virtual;
    private String recommendCode;

    public static void start(Context context, ShopBean shop) {
        Intent starter = new Intent(context, ConfirmOrderGoodsActivity.class);
        starter.putExtra("shop", shop);
        context.startActivity(starter);
    }

//    public static void start(Context context, ArrayList<ShopBean> selectedShops, double totalGoodsPrice) {
//        Intent starter = new Intent(context, ConfirmOrderGoodsActivity.class);
//        starter.putParcelableArrayListExtra("selectedShops", selectedShops);
//        starter.putExtra("totalGoodsPrice", totalGoodsPrice);
//        context.startActivity(starter);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        present = new ConfirmOrderPresentImpl(this, this);
        initVariable();
        initView();
        setChangeViewInfo();
        setListener();
        if (needExpress) {
            bottomLayout.setVisibility(View.GONE);
            present.getDefaultAddress(switcherLayout);
        }
    }


    private void initVariable() {
        if (getIntent() == null) return;
        payType = PAY_ALI;
        days = DateUtils.getSevenDate();
        limit_days = DateUtils.getLimitDays(SystemInfoUtils.getLimit_days(this), SystemInfoUtils.getLimit_period(this));
        expressPrice = SystemInfoUtils.getExpressPrice(this);

        shopBeanList = new ArrayList<>();
        ShopBean shop = getIntent().getParcelableExtra("shop");
        shopBeanList.add(shop);
        GoodsBean goods = shop.getItem().get(0);
        skuCode = goods.getCode();
        amount = FormatUtil.parseInt(goods.getAmount());
        totalGoodsPrice = FormatUtil.parseDouble(goods.getPrice()) * amount;
        pickUpWay = shop.getPickUp().getType();
        if (DELIVERY_SELF.equals(pickUpWay)) {
            pickUpId = shop.getPickUp().getInfo().getId();
        }
        settlementType = goods.getType();
        recommendCode = goods.getRecommendCode();

        //把shopBeanList转为GoodsList 重要代码
        List<GoodsBean> goodsList = new ArrayList<>();
        for (ShopBean shopBean : shopBeanList) {
            for (GoodsBean goodsBean : shopBean.getItem()) {
                goodsList.add(goodsBean);
            }
        }

        if (shopBeanList != null && !shopBeanList.isEmpty() && shopBeanList.get(0).getItem() != null
                && !shopBeanList.get(0).getItem().isEmpty()
                && shopBeanList.get(0).getItem().get(0).is_virtual()) {
            is_virtual = true;
        } else {
            is_virtual = false;
        }
    }

    private void initView() {
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this, contentLayout);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        emptyAddressLayout = (RelativeLayout) findViewById(R.id.rl_empty_address);
        addressLayout = (RelativeLayout) findViewById(R.id.rl_address);
        ivDefault = (ImageView) findViewById(R.id.iv_default);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        selfDeliveryLayout = (LinearLayout) findViewById(R.id.ll_self_delivery);
        tvTime = (TextView) findViewById(R.id.tv_time);
        llReceivingTime = (LinearLayout) findViewById(ll__receiving_time);
        txtrecevingtime = (TextView) findViewById(txt_receving_time);
        txt_self_delivery_identify = (TextView) findViewById(R.id.txt_self_delivery_identify);
        txt_receiving_left_identify = (TextView) findViewById(R.id.txt_receiving_left_identify);


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

        shopAdapter = new ConfirmOrderShopAdapter(this, is_virtual);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.setNestedScrollingEnabled(false);
        rvGoods.setAdapter(shopAdapter);
        shopAdapter.setData(shopBeanList);


    }

    private void setChangeViewInfo() {
        goodsIdGymID.clear();
        itemFromIdAmount.clear();

        for (ShopBean shopBean : shopBeanList) {
            if (DELIVERY_EXPRESS.equals(shopBean.getPickUp().getType())) {
                needExpress = true;
            }

            if (DELIVERY_SELF.equals(shopBean.getPickUp().getType())) {
                needSelfDelivery = true;
            }
            for (GoodsBean goodsBean : shopBean.getItem()) {
                if (needSelfDelivery) {
                    itemFromIdAmount.add(goodsBean.getCouponGoodsType() + "_"
                            + goodsBean.getCode() + "_" + goodsBean.getAmount() + "_" + shopBean.getPickUp().getInfo().getId());

                    goodsIdGymID.put(goodsBean.getCode(), shopBean.getPickUp().getInfo().getId());
                    Logger.i("coupon", "if (shopBean.getPickUp() != null goodsIdGymID.put = " + shopBean.getPickUp().getInfo().getId());
                } else {
                    goodsIdGymID.put(goodsBean.getCode(), "0");
                    itemFromIdAmount.add(goodsBean.getCouponGoodsType() + "_"
                            + goodsBean.getCode() + "_" + goodsBean.getAmount() + "_0");

                    Logger.i("coupon", "if } else { goodsIdGymID.put = 0");
                }
            }
        }


        if (needExpress) {
            addressLayout.setVisibility(View.VISIBLE);
            if (TextUtils.equals(GOODS_FOODS, shopBeanList.get(0).getItem().get(0).getType())) {
                llReceivingTime.setVisibility(View.VISIBLE);
            }
            pickUpWay = DELIVERY_EXPRESS;
            pickUpDate = null;
        }

        if (needSelfDelivery) {
            pickUpDate = days.get(0);
            tvTime.setText(pickUpDate);
            selfDeliveryLayout.setVisibility(View.VISIBLE);
            pickUpWay = DELIVERY_SELF;
            pickUpId = shopBeanList.get(0).getPickUp().getInfo().getId();

        }

        tvTotalGoodsPrice.setRightContent(
                String.format(getString(R.string.rmb_price_double), totalGoodsPrice));
        tvExpressPrice.setRightContent(
                needExpress ? String.format(getString(R.string.rmb_price_double), expressPrice) : "¥ 0.00");
        double dPrice = needExpress ? expressPrice : 0d;
        double cPrice = !TextUtils.isEmpty(couponPrice) ? FormatUtil.parseDouble(couponPrice) : 0d;
        tvFinalPrice.setText(String.format(getString(R.string.rmb_price_double), totalGoodsPrice + dPrice - cPrice));

//        if (shopBean != null && shopBean.getItem() != null && !shopBean.getItem().isEmpty() &&
//                (TextUtils.equals(GOODS_FOODS, shopBean.getItem().get(0).getType()) ||
//                        (TextUtils.equals(GOODS_NUTRITION, shopBean.getItem().get(0).getType())
//                                && shopBean.getItem().get(0).getProductIdInteger() > Constant.GOODS_FOODS_START_INDEX))) {

        if (shopBeanList != null && !shopBeanList.isEmpty() && shopBeanList.get(0).getItem() != null &&
                !shopBeanList.get(0).getItem().isEmpty() && GOODS_FOODS.equals(shopBeanList.get(0).getItem().get(0).getType())) {

            //该商品为健康餐饮
            receivingTimeQuantum = SystemInfoUtils.getPeriods(this);
            pickUpDate = limit_days.get(0);
            pick_up_period = receivingTimeQuantum.get(0);
            txtrecevingtime.setText(pickUpDate + " " + pick_up_period);
            tvTime.setText(pickUpDate + " " + pick_up_period);
            txt_receiving_left_identify.setText(R.string.send_the_meal_time);
            txt_self_delivery_identify.setText(R.string.send_the_meal_time);
        }

        if (is_virtual) {
            //是虚拟商品
            findViewById(R.id.layout_coupon).setVisibility(View.GONE);
            findViewById(R.id.inc_goods_line).setVisibility(View.GONE);
            findViewById(R.id.view_coupon_line).setVisibility(View.GONE);
            findViewById(R.id.inc_line_with_margin).setVisibility(View.GONE);
            emptyAddressLayout.setVisibility(View.GONE);
            addressLayout.setVisibility(View.GONE);
            selfDeliveryLayout.setVisibility(View.GONE);
            llReceivingTime.setVisibility(View.GONE);
        } else {
            findViewById(R.id.layout_coupon).setVisibility(View.VISIBLE);
            findViewById(R.id.inc_goods_line).setVisibility(View.VISIBLE);
            findViewById(R.id.view_coupon_line).setVisibility(View.VISIBLE);
            present.getGoodsAvailableCoupon(itemFromIdAmount, goodsIdGymID);

        }

    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        emptyAddressLayout.setOnClickListener(this);
        tvCoupon.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        selfDeliveryLayout.setOnClickListener(this);
        llReceivingTime.setOnClickListener(this);
        shopAdapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_self_delivery:

//                Logger.i(TAG,shopBeanList.get(0).getItem().get(0).getType());
                if (shopBeanList != null && !shopBeanList.isEmpty() && shopBeanList.get(0).getItem() != null &&
                        !shopBeanList.get(0).getItem().isEmpty() && GOODS_FOODS.equals(shopBeanList.get(0).getItem().get(0).getType())) {
                    showReceivingDateDialog();
                } else {
                    showDeliveryDateDialog();
                }

                break;
            case R.id.ll__receiving_time:
                showReceivingDateDialog();
                break;
            case R.id.rl_empty_address:
                startActivityForResult(new Intent(this, AddAddressActivity.class), REQUEST_ADD_ADDRESS);
                break;
            case R.id.rl_address:
                startActivityForResult(new Intent(this, SelectAddressActivity.class).putExtra("addressId", addressId),
                        REQUEST_SELECT_ADDRESS);
                break;
            case R.id.tv_coupon:
                if (usableCoupons != null && !usableCoupons.isEmpty() && totalGoodsPrice > 0) {
                    Logger.i("coupon", "startForResult selectedUserCouponId = " + selectedUserCouponId);
                    SelectCouponActivity.startForResult(this, String.valueOf(totalGoodsPrice), couponId, selectedUserCouponId, usableCoupons, REQUEST_SELECT_COUPON);
                }
                break;
            case R.id.tv_pay:
                payOrder();
                break;
            default:
                break;
        }
    }


    private void showDeliveryDateDialog() {
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

    private void showReceivingDateDialog() {

        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                pickUpDate = limit_days.get(options1);
                pick_up_period = receivingTimeQuantum.get(options2);
                txtrecevingtime.setText(pickUpDate + " " + pick_up_period);
                tvTime.setText(pickUpDate + " " + pick_up_period);
            }
        }).build();
        pvNoLinkOptions.setNPicker(limit_days, receivingTimeQuantum, null);
        pvNoLinkOptions.show();

    }

    @Override
    public void onDeliveryTypeClick(int position) {
        Logger.i("coupon select", "onDeliveryTypeClick requestCode = " + REQUEST_UPDATE_DELIVERY);
        UpdateDeliveryInfoActivity.startForResult(this, (ArrayList<ShopBean>) shopBeanList, position, REQUEST_UPDATE_DELIVERY);
    }

    private void payOrder() {
        if (needExpress) {
            if (TextUtils.isEmpty(pickUpId)) {
                ToastGlobal.showLong("请填写收货地址");
                return;
            }
        }
        Logger.i(TAG, "pickUpDate = " + pickUpDate + "pick_up_period =" + pick_up_period);
        present.buyGoodsImmediately(settlementType, skuCode, amount, couponId, integral, coin, payType,
                String.valueOf(pickUpWay), pickUpId, pickUpDate, pick_up_period, "0", payListener, recommendCode);

    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            //ToastGlobal.showLong("支付成功");
            LocalBroadcastManager.getInstance(ConfirmOrderGoodsActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS));
            PaySuccessActivity.start(ConfirmOrderGoodsActivity.this, present.getShareInfo(), is_virtual);
            finish();
        }

        @Override
        public void onFail(String code, Object object) {
            super.onFail(code, object);
            LocalBroadcastManager.getInstance(ConfirmOrderGoodsActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL));

            Intent intent = new Intent(ConfirmOrderGoodsActivity.this, AppointmentMineActivityNew.class);
            intent.putExtra("tranPosition", 6);


            startActivity(intent);
            finish();
        }

        @Override
        public void onFree() {
            LocalBroadcastManager.getInstance(ConfirmOrderGoodsActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS));
            PaySuccessActivity.start(ConfirmOrderGoodsActivity.this, present.getShareInfo(), is_virtual);
            ToastGlobal.showLong("支付成功");
            finish();
        }
    };


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
    public void setDefaultAddressResult(AddressBean address) {
        bottomLayout.setVisibility(View.VISIBLE);
        updateAddressStatus(address);
    }

    @Override
    public void setSpecifyGoodsCouponResult(List<CouponBean> usableCoupons) {
        this.usableCoupons = usableCoupons;
        if (usableCoupons == null || usableCoupons.isEmpty()) {
            tvCoupon.setText("无可用");
            //tvCoupon.setCompoundDrawables(null, null, null, null);
            tvCoupon.setTextColor(ContextCompat.getColor(this,R.color.c9));
        }else {
            tvCoupon.setText("请选择");
            tvCoupon.setTextColor(Color.BLACK);
        }
        //把优惠券使用置为初始状态

        selectedUserCouponId = null;
        Logger.i("coupon", "setSpecifyGoodsCouponResult = " + selectedUserCouponId);
        couponId = null;
        couponPrice = null;

        tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double), 0d));
        double dPrice = needExpress ? expressPrice : 0d;
        double cPrice = !TextUtils.isEmpty(couponPrice) ? FormatUtil.parseDouble(couponPrice) : 0d;
        tvFinalPrice.setText(String.format(getString(R.string.rmb_price_double), totalGoodsPrice + dPrice - cPrice));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i("coupon select", "onActivityResult requestCode = " + requestCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_ADDRESS) {
                AddressBean address = data.getParcelableExtra("address");
                setAddressInfo(address);
            } else if (requestCode == REQUEST_ADD_ADDRESS) {
                AddressBean address = data.getParcelableExtra("address");
                updateAddressStatus(address);
            } else if (requestCode == REQUEST_SELECT_COUPON) {
                CouponBean couponBean = data.getParcelableExtra("coupon");

                selectedUserCouponId = couponBean.getUser_coupon_id();
                Logger.i("coupon", "onActivityResult selectedUserCouponId = " + selectedUserCouponId);
                couponId = couponBean.getId();
                couponPrice = couponBean.getActual();
                tvCoupon.setText(FormatUtil.parseDouble(couponPrice) >= 0
                        ? String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getActual())) : getString(R.string.please_select));
                tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double), FormatUtil.parseDouble(couponPrice)));
                double dPrice = needExpress ? expressPrice : 0;
                double cPrice = !TextUtils.isEmpty(couponPrice) ? FormatUtil.parseDouble(couponPrice) : 0d;
                tvFinalPrice.setText(String.format(getString(R.string.rmb_price_double), totalGoodsPrice + dPrice - cPrice));
            } else if (requestCode == REQUEST_UPDATE_DELIVERY) {
                shopBeanList = data.getParcelableArrayListExtra("shopList");
                shopAdapter.setData(shopBeanList);
                resetStatus();
                setChangeViewInfo();
                if (needExpress) {
                    bottomLayout.setVisibility(View.GONE);
                    present.getDefaultAddress(switcherLayout);
                }
            }
        }
    }

    private void updateAddressStatus(AddressBean address) {
        if (address == null) {
            addressLayout.setVisibility(View.GONE);
            emptyAddressLayout.setVisibility(View.VISIBLE);
        } else {
            setAddressInfo(address);
            addressLayout.setVisibility(View.VISIBLE);
            emptyAddressLayout.setVisibility(View.GONE);
        }
    }

    private void setAddressInfo(AddressBean address) {

        Logger.i("coupon select", "onActivityResult setAddressInfo = " + address.getId());
        ivDefault.setVisibility(address.isDefault() ? View.VISIBLE : View.GONE);
        tvName.setText(address.getName());
        tvPhone.setText(address.getMobile());
        StringBuilder sb = new StringBuilder("收货地址: ");
        sb.append(address.getCity().contains(address.getProvince()) ? "" : address.getProvince()).append(address.getCity())
                .append(address.getDistrict()).append(address.getAddress());
        tvAddress.setText(sb);
        pickUpId = address.getId();
    }

    private void resetStatus() {
        needExpress = false;
        needSelfDelivery = false;
        addressId = null;
        pickUpId = null;
        pickUpDate = null;
        addressLayout.setVisibility(View.GONE);
        llReceivingTime.setVisibility(View.GONE);
        selfDeliveryLayout.setVisibility(View.GONE);
    }
}
