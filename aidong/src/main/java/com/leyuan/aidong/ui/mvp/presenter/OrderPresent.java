package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public interface OrderPresent {

    /**
     * 第一次加载订单列表数据
     * @param list           list all: 全部 self-delivery: 自提 express-delivery: 快递
     */
    void commonLoadData(String list);


    /**
     * 第一次加载订单列表数据
     *
     * @param switcherLayout SwitcherLayout
     * @param list           list all: 全部 self-delivery: 自提 express-delivery: 快递
     */
    void commonLoadData(SwitcherLayout switcherLayout, String list);

    /**
     * 下拉刷新订单列表
     *
     * @param list all: 全部 self-delivery: 自提 express-delivery: 快递
     */
    void pullToRefreshData(String list);

    /**
     * 上拉加载更多订单列表
     *
     * @param recyclerView RecyclerView
     * @param list         all: 全部 self-delivery: 自提 express-delivery: 快递
     * @param pageSize     每页加载数
     * @param page         页码
     */
    void requestMoreData(RecyclerView recyclerView, String list, int pageSize, int page);

    /**
     * 获取订单
     *
     * @param id             订单id
     */
    void getOrderDetail(String id);

    /**
     * 获取订单
     *
     * @param id             订单id
     * @param switcherLayout SwitcherLayout
     */
    void getOrderDetail(SwitcherLayout switcherLayout,String id);


    /**
     * 取消订单
     *
     * @param id
     */
    void cancelOrder(String id);

    /**
     * 确认订单
     *
     * @param id
     */
    void confirmOrder(String id);

    /**
     * 删除订单
     *
     * @param id
     */
    void deleteOrder(String id);


    /**
     * 订单退换货
     */
    void feedbackOrder(String id, String type, String[] items, String content, String[] image, String address);

    /**
     * 再次购买
     * @param orderId
     */
    void reBuyOrder(String orderId);

    /**
     * 获取快递信息
     * @param orderId
     */
    void getExpressInfo(String orderId);

    ShareData.ShareCouponInfo getShareInfo();
}
