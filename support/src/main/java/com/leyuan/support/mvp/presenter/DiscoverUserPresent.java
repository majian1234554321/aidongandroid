package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 发现 - 人
 * Created by song on 2016/8/29.
 */
public interface DiscoverUserPresent {

    void pullToRefreshData(RecyclerView recyclerView, double lat, double lng, String gender, String type);

    /**
     * 上拉加载更多
     */
    void requestMoreData(RecyclerView recyclerView, double lat, double lng, String gender, String type,int size, int page);
}
