package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public interface OrderPresent {

    /**
     * 第一次加载订单列表数据
     * @param switcherLayout SwitcherLayout
     * @param list list all: 全部 self-delivery: 自提 express-delivery: 快递
     */
    void commonLoadData(SwitcherLayout switcherLayout,String list);

    /**
     * 下拉刷新订单列表
     * @param list all: 全部 self-delivery: 自提 express-delivery: 快递
     */
    void pullToRefreshData(String list);

    /**
     * 上拉加载更多订单列表
     * @param recyclerView RecyclerView
     * @param list all: 全部 self-delivery: 自提 express-delivery: 快递
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView,String list, int pageSize, int page);

    /**
     * 获取订单
     * @param id 订单id
     * @param switcherLayout SwitcherLayout
     */
    void getOrderDetail(String id,SwitcherLayout switcherLayout);
}
