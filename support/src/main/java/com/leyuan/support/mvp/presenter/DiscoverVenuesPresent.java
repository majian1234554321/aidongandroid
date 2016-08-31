package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 发现 - 场馆
 * Created by song on 2016/8/29.
 */
public interface DiscoverVenuesPresent {

    /**
     * 下拉刷新
     * @param recyclerView RecyclerView
     * @param lat 纬度
     * @param lng 经度
     */
    void pullToRefreshData(RecyclerView recyclerView, double lat, double lng);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param lat 纬度
     * @param lng 经度
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, double lat, double lng , int pageSize,int page);
}
