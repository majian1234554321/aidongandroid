package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.OrderDetailBean;

import java.util.List;

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
     * 取消订单
     * @param baseBean
     */
    void cancelOrderResult(BaseBean baseBean);

    /**
     * 确认订单
     * @param baseBean
     */
    void confirmOrderResult(BaseBean baseBean);

    /**
     * 删除订单
     * @param baseBean
     */
    void deleteOrderResult(BaseBean baseBean);


    /**
     * 再次购买
     * @param cartIds
     */
    void reBuyOrderResult(List<String> cartIds);
}
