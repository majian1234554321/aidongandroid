package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.data.OrderData;
import com.leyuan.support.entity.data.OrderDetailData;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.OrderService;
import com.leyuan.support.mvp.model.OrderModel;

import rx.Subscriber;

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
    public void getOrderDetail(Subscriber<OrderDetailData> subscriber, int id) {
        orderService.getOrderDetail(id)
                .compose(RxHelper.<OrderDetailData>transform())
                .subscribe(subscriber);
    }
}
