package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .entity.data.ShopData;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.CartService;
import com.example.aidong .ui.mvp.model.CartModel;

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
    public void updateDeliveryInfo(Subscriber<BaseBean> subscriber, String id, int mount,String gymId) {
        cartService.updateCart(id,mount,gymId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void updateDeliveryInfo(Subscriber<BaseBean> subscriber, String id,String count, String gym_id) {
        cartService.updateDeliveryInfo(id,count,gym_id)
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
