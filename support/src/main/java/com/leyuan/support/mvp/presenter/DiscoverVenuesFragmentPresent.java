package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 发现-场馆
 * Created by Song on 2016/8/2.
 */
public interface DiscoverVenuesFragmentPresent {

    /**
     * 下拉刷新
     */
     void pullToRefreshData(RecyclerView recyclerView);

    /**
     * 上拉加载更多
     */
     void requestMoreData(RecyclerView recyclerView, int size, int page);

    /**
     * 查找场馆
     */
     void searchVenues();
}
