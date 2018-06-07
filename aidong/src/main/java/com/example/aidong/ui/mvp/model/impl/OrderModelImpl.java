package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CartIdBean;
import com.example.aidong .entity.ExpressBean;
import com.example.aidong .entity.OrderDetailExpressBean;
import com.example.aidong .entity.data.OrderData;
import com.example.aidong .entity.data.OrderDetailData;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.OrderService;
import com.example.aidong .ui.mvp.model.OrderModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public class OrderModelImpl implements OrderModel {
    private OrderService orderService;

    public OrderModelImpl() {
        orderService = RetrofitHelper.createApi(OrderService.class);
    }

    @Override
    public void getOrders(Subscriber<OrderData> subscriber, String list, int page) {
        orderService.getOrders(list, page)
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

    @Override
    public void feedbackOrder(Subscriber<BaseBean> subscriber, String id, String type, String[] items,
                              String content, String[] image, String address) {

        orderService.feedbackOrder(id, type, items, content, image, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void reBuyOrder(Subscriber<CartIdBean> subscriber, String orderId) {
        orderService.reBuyOrder(orderId)
                .compose(RxHelper.<CartIdBean>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getExpressInfo(Subscriber<ExpressBean> subscriber, String orderId) {
        orderService.getExpressInfo(orderId)
                .compose(RxHelper.<ExpressBean>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getOrderDetailExpressInfo(Subscriber<OrderDetailExpressBean> subscriber, String orderId) {
        orderService.getOrderDetailExpressInfo(orderId)
                .compose(RxHelper.<OrderDetailExpressBean>transform())
                .subscribe(subscriber);
    }
}
