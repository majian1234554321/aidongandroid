package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.ShopData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CartService;
import com.leyuan.aidong.ui.mvp.model.CartModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartModelImpl implements CartModel{

    private CartService cartService;

    public CartModelImpl() {
        cartService = RetrofitHelper.createApi(CartService.class);
    }

    @Override
    public void getCart(Subscriber<ShopData> subscriber) {
        cartService.getCart()
            .compose(RxHelper.<ShopData>transform())
            .subscribe(subscriber);
    }

    @Override
    public void addCart(Subscriber<BaseBean> subscriber, String skuCode, int mount,String gymId,String recommendId) {
        cartService.addCart(skuCode,mount,gymId,recommendId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deleteCart(Subscriber<BaseBean> subscriber, String ids) {
        cartService.deleteCart(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void updateDeliveryInfo(Subscriber<BaseBean> subscriber, String id, int mount) {
        cartService.updateCart(id,mount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void updateDeliveryInfo(Subscriber<BaseBean> subscriber, String id, String gym_id) {
        cartService.updateDeliveryInfo(id,gym_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void payCart(Subscriber<PayOrderData> subscriber, String integral, String coin,
                        String coupon, String payType, String pickUpId, String pickUpDate, String... id) {
        cartService.payCart(integral,coin,coupon,payType,pickUpId,pickUpDate,id)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }
}
