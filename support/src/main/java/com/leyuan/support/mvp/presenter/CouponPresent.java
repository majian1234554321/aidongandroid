package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 优惠劵
 * Created by song on 2016/9/14.
 */
public interface CouponPresent {

    /**
     * 第一次加载数据
     * @param switcherLayout SwitcherLayout
     * @param type valid: 有效的 used: 已使用的 expired:已过期的
     */
    void commonLoadData(SwitcherLayout switcherLayout, String type);

    /**
     * 下拉刷新
     * @param type valid: 有效的 used: 已使用的 expired:已过期的
     */
    void pullToRefreshData(String type);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param type valid: 有效的 used: 已使用的 expired:已过期的
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, String type, int pageSize, int page);
}
