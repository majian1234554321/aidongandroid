package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CartIdBean;
import com.leyuan.aidong.entity.ExpressBean;
import com.leyuan.aidong.entity.data.OrderData;
import com.leyuan.aidong.entity.data.OrderDetailData;

import rx.Subscriber;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public interface OrderModel {

    /**
     * 获取订单列表
     *
     * @param subscriber Subscriber
     * @param list       all: 全部 self-delivery: 自提 express-delivery: 快递
     * @param page       页码
     */
    void getOrders(Subscriber<OrderData> subscriber, String list, int page);


    /**
     * 获取订单详情
     *
     * @param subscriber Subscriber
     * @param id         订单id
     */
    void getOrderDetail(Subscriber<OrderDetailData> subscriber, String id);


    /**
     * 取消订单
     *
     * @param subscriber
     * @param id         订单id
     */
    void cancelOrder(Subscriber<BaseBean> subscriber, String id);

    /**
     * 确认订单
     *
     * @param subscriber
     * @param id         订单id
     */
    void confirmOrder(Subscriber<BaseBean> subscriber, String id);

    /**
     * 删除订单
     *
     * @param subscriber
     * @param id         订单id
     */
    void deleteOrder(Subscriber<BaseBean> subscriber, String id);

    void feedbackOrder(Subscriber<BaseBean> subscriber, String id, String type, String[] items,
                       String content, String[] image, String address);

    /**
     * 再次购买
     * @param subscriber
     * @param orderId
     */
    void reBuyOrder(Subscriber<CartIdBean> subscriber, String orderId);

    /**
     * 获取快递信息
     * @param subscriber
     * @param orderId
     */
    void getExpressInfo(Subscriber<ExpressBean> subscriber,String orderId);
}

