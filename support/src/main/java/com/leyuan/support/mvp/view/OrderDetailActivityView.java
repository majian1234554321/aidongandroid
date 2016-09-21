package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.OrderDetailBean;

/**
 * 订单详情
 * Created by song on 2016/8/30.
 */
public interface OrderDetailActivityView {

    /**
     * 设置订单详情
     * @param orderDetailBean 订单详情实体
     */
    void setOrderDetail(OrderDetailBean orderDetailBean);


    /**
     * 显示无网络界面
     */
    void showErrorView();
}
