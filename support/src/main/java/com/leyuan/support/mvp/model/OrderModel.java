package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.data.OrderData;
import com.leyuan.support.entity.data.OrderDetailData;

import rx.Subscriber;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public interface OrderModel {

    /**
     * 获取订单列表
     * @param subscriber Subscriber
     * @param list all: 全部 self-delivery: 自提 express-delivery: 快递
     * @param page 页码
     */
    void getOrders(Subscriber<OrderData> subscriber,String  list,int page);


    /**
     * 获取订单详情
     * @param subscriber Subscriber
     * @param id 订单id
     */
    void getOrderDetail(Subscriber<OrderDetailData> subscriber,String id);
}
