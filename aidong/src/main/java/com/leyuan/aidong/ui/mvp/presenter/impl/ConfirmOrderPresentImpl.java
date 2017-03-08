package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.data.AddressListData;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.mvp.model.AddressModel;
import com.leyuan.aidong.ui.mvp.model.CartModel;
import com.leyuan.aidong.ui.mvp.model.CouponModel;
import com.leyuan.aidong.ui.mvp.model.EquipmentModel;
import com.leyuan.aidong.ui.mvp.model.NurtureModel;
import com.leyuan.aidong.ui.mvp.model.impl.AddressModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.CartModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.CouponModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.EquipmentModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.NurtureModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.ConfirmOrderPresent;
import com.leyuan.aidong.ui.mvp.view.ConfirmOrderActivityView;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.List;

/**
 * 确认订单
 * Created by song on 2017/3/8.
 */
public class ConfirmOrderPresentImpl implements ConfirmOrderPresent{
    private Context context;
    private CartModel cartModel;
    private CouponModel couponModel;
    private AddressModel addressModel;
    private NurtureModel nurtureModel;
    private EquipmentModel equipmentModel;
    private ConfirmOrderActivityView orderActivityView;

    public ConfirmOrderPresentImpl(Context context, ConfirmOrderActivityView orderActivityView) {
        this.context = context;
        this.orderActivityView = orderActivityView;
        addressModel = new AddressModelImpl();
    }

    @Override
    public void getDefaultAddress(final SwitcherLayout switcherLayout) {
        addressModel.getAddress(new CommonSubscriber<AddressListData>(switcherLayout) {
            @Override
            public void onNext(AddressListData addressListData) {
                AddressBean addressBean = null;
                if(addressListData != null){
                    List<AddressBean> addressList = addressListData.getAddress();
                    for (AddressBean bean : addressList) {
                        if(bean.isDefault()){
                            addressBean = bean;
                        }
                    }
                    if(addressBean == null && !addressList.isEmpty()){
                        addressBean = addressList.get(0);
                    }
                }
                switcherLayout.showContentLayout();
                orderActivityView.setDefaultAddressResult(addressBean); //maybe null
            }
        });
    }

    @Override
    public void getSpecifyGoodsCoupon(String from, String... id) {
        if(couponModel == null) {
            couponModel = new CouponModelImpl();
        }
        couponModel.getSpecifyGoodsCoupon(new ProgressSubscriber<CouponData>(context,false) {
            @Override
            public void onNext(CouponData couponData) {
                if(couponData != null) {
                    orderActivityView.setSpecifyGoodsCouponResult(couponData.getCoupon());//maybe null
                }
            }
        },from,id);
    }

    @Override
    public void buyEquipmentImmediately(String skuCode, int amount, String coupon, String integral,
                                        String coin, String payType, String pickUpWay, String pickUpId,
                                        String pickUpDate,final PayInterface.PayListener listener) {
        if(equipmentModel == null){
            equipmentModel = new EquipmentModelImpl(context);
        }
        equipmentModel.buyEquipmentImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                String payType = payOrderData.getOrder().getPayType();
                PayInterface payInterface = PayType.ALI.equals(payType) ? new AliPay(context,listener)
                        : new WeiXinPay(context,listener);
                payInterface.payOrder(payOrderData.getOrder());
            }
        },skuCode,amount,coupon,integral,coin,payType,pickUpWay,pickUpId,pickUpDate);
    }

    @Override
    public void buyNurtureImmediately(String skuCode, int amount, String coupon, String integral,
                                      String coin, String payType, String pickUpWay, String pickUpId,
                                      String pickUpDate,final PayInterface.PayListener listener) {
        if(nurtureModel == null){
            nurtureModel = new NurtureModelImpl(context);
        }
        nurtureModel.buyNurtureImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                String payType = payOrderData.getOrder().getPayType();
                PayInterface payInterface = PayType.ALI.equals(payType) ? new AliPay(context,listener)
                        : new WeiXinPay(context,listener);
                payInterface.payOrder(payOrderData.getOrder());
            }
        },skuCode,amount,coupon,integral,coin,payType,pickUpWay,pickUpId,pickUpDate);
    }

    @Override
    public void payCart(String integral, String coin, String coupon, String payType, String pickUpId,
                        String pickUpDate, final PayInterface.PayListener listener, String... id) {
        if(cartModel == null) {
            cartModel = new CartModelImpl();
        }
        cartModel.payCart(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                String payType = payOrderData.getOrder().getPayType();
                PayInterface payInterface = PayType.ALI.equals(payType) ? new AliPay(context,listener)
                        : new WeiXinPay(context,listener);
                payInterface.payOrder(payOrderData.getOrder());
            }
        },integral,coin,coupon,payType,pickUpId,pickUpDate,id);
    }
}
