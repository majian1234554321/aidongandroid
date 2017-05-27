package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.PayOrderBean;
import com.leyuan.aidong.entity.ShareBean;
import com.leyuan.aidong.entity.data.AddressListData;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.ShopData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
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
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.List;

/**
 * 确认订单
 * Created by song on 2017/3/8.
 */
public class ConfirmOrderPresentImpl implements ConfirmOrderPresent {
    private Context context;
    private CartModel cartModel;
    private CouponModel couponModel;
    private AddressModel addressModel;
    private NurtureModel nurtureModel;
    private EquipmentModel equipmentModel;
    private ConfirmOrderActivityView orderActivityView;

    public ShareBean shareBean;

    public ConfirmOrderPresentImpl(Context context, ConfirmOrderActivityView orderActivityView) {
        this.context = context;
        this.orderActivityView = orderActivityView;
        addressModel = new AddressModelImpl();
    }

    @Override
    public void getDefaultAddress(final SwitcherLayout switcherLayout) {
        addressModel.getAddress(new CommonSubscriber<AddressListData>(context, switcherLayout) {
            @Override
            public void onNext(AddressListData addressListData) {
                AddressBean addressBean = null;
                if (addressListData != null) {
                    List<AddressBean> addressList = addressListData.getAddress();
                    for (AddressBean bean : addressList) {
                        if (bean.isDefault()) {
                            addressBean = bean;
                        }
                    }
                    if (addressBean == null && !addressList.isEmpty()) {
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
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
        couponModel.getSpecifyGoodsCoupon(new ProgressSubscriber<CouponData>(context, false) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    orderActivityView.setSpecifyGoodsCouponResult(couponData.getCoupon());//maybe null
                }
            }
        }, from, id);
    }

    @Override
    public void getGoodsAvailableCoupon(String... items) {
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
        couponModel.getGoodsAvailableCoupon(new ProgressSubscriber<CouponData>(context, false) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    orderActivityView.setSpecifyGoodsCouponResult(couponData.getCoupon());//maybe null
                }
            }
        }, items);
    }

    @Override
    public void buyEquipmentImmediately(String skuCode, int amount, String coupon, String integral,
                                        String coin, String payType, String pickUpWay, String pickUpId,
                                        String pickUpDate, final PayInterface.PayListener listener) {
        if (equipmentModel == null) {
            equipmentModel = new EquipmentModelImpl(context);
        }
        equipmentModel.buyEquipmentImmediately(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                PayUtils.pay(context, payOrderData.getOrder(), listener);
                createShareBeanByOrder(payOrderData);
            }
        }, skuCode, amount, coupon, integral, coin, payType, pickUpWay, pickUpId, pickUpDate);
    }

    @Override
    public void buyNurtureImmediately(String skuCode, int amount, String coupon, String integral,
                                      String coin, String payType, String pickUpWay, String pickUpId,
                                      String pickUpDate, final PayInterface.PayListener listener) {
        if (nurtureModel == null) {
            nurtureModel = new NurtureModelImpl(context);
        }
        nurtureModel.buyNurtureImmediately(new ProgressSubscriber<PayOrderData>(context) {

            @Override
            public void onNext(PayOrderData payOrderData) {

                PayUtils.pay(context, payOrderData.getOrder(), listener);
                createShareBeanByOrder(payOrderData);

            }
        }, skuCode, amount, coupon, integral, coin, payType, pickUpWay, pickUpId, pickUpDate);
    }

    private void createShareBeanByOrder(PayOrderData payOrderData) {
        if (payOrderData.getOrder() != null && payOrderData.getOrder().getShare_coupons() != null) {
            PayOrderBean payOrderBean = payOrderData.getOrder();
            shareBean = new ShareBean("爱动健身", "优惠券分享"
                    , "http://function.aidong.me/image/2017/03/16/1926f2870e1736340cca5cc54ded0e03.jpg"
                    , ShareBean.createCouponShare(payOrderBean.getCreatedAt(),
                    payOrderBean.getId(), payOrderBean.getShare_coupons()));
        }
    }

    @Override
    public void payCart(String integral, String coin, String coupon, String payType, String pickUpId,
                        String pickUpDate, final PayInterface.PayListener listener, String... id) {
        if (cartModel == null) {
            cartModel = new CartModelImpl();
        }
        cartModel.payCart(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                PayUtils.pay(context, payOrderData.getOrder(), listener);
            }
        }, integral, coin, coupon, payType, pickUpId, pickUpDate, id);
    }

    @Override
    public void refreshCartData() {
        if (cartModel == null) {
            cartModel = new CartModelImpl();
        }

        cartModel.getCart(new RefreshSubscriber<ShopData>(context) {
            @Override
            public void onNext(ShopData shopData) {
                if (shopData != null && shopData.getCart() != null && !shopData.getCart().isEmpty()) {
                    // orderActivityView.setRefreshCartDataResult(shopData.getCart());
                }
            }
        });
    }

    @Override
    public ShareBean getShareBean() {
        return shareBean;
    }

}
