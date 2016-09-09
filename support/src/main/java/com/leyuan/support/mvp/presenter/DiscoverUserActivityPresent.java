package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 发现 - 人
 * Created by song on 2016/8/29.
 */
public interface DiscoverUserActivityPresent {
    /**
     * 第一次正常加载数据
     */
    void commonLoadData(SwitcherLayout switcherLayout,double lat, double lng, String gender, String type);

    /**
     * 下拉刷新
     */
    void pullToRefreshData(double lat, double lng, String gender, String type);

    /**
     * 上拉加载更多
     */
    void requestMoreData(RecyclerView recyclerView, double lat, double lng, String gender, String type,int size, int page);
}
