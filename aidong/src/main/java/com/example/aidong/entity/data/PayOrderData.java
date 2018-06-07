package com.example.aidong.entity.data;

import com.example.aidong .entity.PayOrderBean;

/**
 * 获取订单服务器返回的实体
 * Created by song on 2016/11/14.
 */
public class PayOrderData {
   private PayOrderBean order;

    public PayOrderBean getOrder() {
        return order;
    }

    public void setOrder(PayOrderBean order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "PayOrderData{" +
                "order=" + order +
                '}';
    }
}
