package com.leyuan.support.entity.data;

import com.leyuan.support.entity.OrderDetailBean;

/**
 * 订单详情
 * Created by song on 2016/9/1.
 */
public class OrderDetailData {
    private OrderDetailBean item;

    public OrderDetailBean getItem() {
        return item;
    }

    public void setItem(OrderDetailBean item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "OrderDetailData{" +
                "item=" + item +
                '}';
    }
}
