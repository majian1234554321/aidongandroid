package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignActivityPresent {
    /**
     * 下拉刷新
     * @param recyclerView 执行刷新的RecyclerView
     */
    void pullToRefreshData(RecyclerView recyclerView);


    /**
     * 上拉加载更多
     * @param recyclerView 执行刷新的RecyclerView
     * @param pageSize 每页刷新的数据量
     * @param page 页码
     */
    //void requestMoreData(RecyclerView recyclerView, int pageSize, int page);
}
