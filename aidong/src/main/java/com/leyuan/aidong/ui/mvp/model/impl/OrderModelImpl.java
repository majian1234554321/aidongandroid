package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.OrderData;
import com.leyuan.aidong.entity.data.OrderDetailData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.OrderService;
import com.leyuan.aidong.ui.mvp.model.OrderModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public class OrderModelImpl implements OrderModel{
    private OrderService orderService;

    public OrderModelImpl() {
        orderService = RetrofitHelper.createApi(OrderService.class);
    }

    @Override
    public void getOrders(Subscriber<OrderData> subscriber, String list,int page) {
        orderService.getOrders(list,page)
                .compose(RxHelper.<OrderData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getOrderDetail(Subscriber<OrderDetailData> subscriber, String id) {
        orderService.getOrderDetail(id)
                .compose(RxHelper.<OrderDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void cancelOrder(Subscriber<BaseBean> subscriber, String id) {
        orderService.cancelOrder(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void confirmOrder(Subscriber<BaseBean> subscriber, String id) {
        orderService.confirmOrder(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deleteOrder(Subscriber<BaseBean> subscriber, String id) {
        orderService.deleteOrder(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
