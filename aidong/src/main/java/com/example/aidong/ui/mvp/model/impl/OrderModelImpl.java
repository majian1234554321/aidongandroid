package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong.entity.data.OrderData;
import com.example.aidong.entity.data.OrderDetailData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.RxHelper;
import com.example.aidong.http.api.OrderService;
import com.example.aidong.ui.mvp.model.OrderModel;

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
    public void getOrderDetail(Subscriber<OrderDetailData> subscriber, String id) {
        orderService.getOrderDetail(id)
                .compose(RxHelper.<OrderDetailData>transform())
                .subscribe(subscriber);
    }
}
