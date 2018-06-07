package com.example.aidong.entity.data;

import com.example.aidong .entity.OrderBean;

import java.util.ArrayList;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public class OrderData {
    private ArrayList<OrderBean> order;

    public ArrayList<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<OrderBean> order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "order=" + order +
                '}';
    }
}
