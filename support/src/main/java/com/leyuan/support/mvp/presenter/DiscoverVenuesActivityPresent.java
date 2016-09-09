package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 发现 - 场馆
 * Created by song on 2016/8/29.
 */
public interface DiscoverVenuesActivityPresent {

    /**
     * 第一次正常加载数据
     * @param switcherLayout SwitcherLayout
     * @param lat 纬度
     * @param lng 经度
     */
    void commonLoadData(SwitcherLayout switcherLayout,double lat, double lng);


    /**
     * 下拉刷新
     * @param lat 纬度
     * @param lng 经度
     */
    void pullToRefreshData(double lat, double lng);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param lat 纬度
     * @param lng 经度
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, double lat, double lng , int pageSize,int page);
}
