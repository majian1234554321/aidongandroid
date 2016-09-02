package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public interface OrderFragmentPresent {

    /**
     * 下拉刷新
     * @param recyclerView RecyclerView
     * @param list all: 全部 self-delivery: 自提 express-delivery: 快递
     */
    void pullToRefreshData(RecyclerView recyclerView,String list);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param list all: 全部 self-delivery: 自提 express-delivery: 快递
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView,String list, int pageSize, int page);
}
