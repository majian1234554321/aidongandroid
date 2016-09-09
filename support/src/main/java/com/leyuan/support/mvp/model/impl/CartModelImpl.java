package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.GoodsBean;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.CartService;
import com.leyuan.support.mvp.model.CartModel;

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
    public void addCart(Subscriber<BaseBean> subscriber, String sku, int mount) {
        cartService.addCart(sku,mount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteCart(Subscriber<BaseBean> subscriber, String ids) {
        cartService.deleteCart(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void updateCart(Subscriber<BaseBean> subscriber, String id, int mount) {
        cartService.updateCart(id,mount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
