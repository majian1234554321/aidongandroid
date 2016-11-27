package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CartService;
import com.leyuan.aidong.ui.mvp.model.CartModel;

import java.util.List;

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
    public void getCart(Subscriber<List<GoodsBean>> subscriber) {
        cartService.getCart()
            .compose(RxHelper.<List<GoodsBean>>transform())
            .subscribe(subscriber);
    }

    @Override
    public void addCart(Subscriber<BaseBean> subscriber, String skuCode, int mount) {
        cartService.addCart(skuCode,mount)
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
    public void updateCart(Subscriber<BaseBean> subscriber, String id, int mount) {
        cartService.updateCart(id,mount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
