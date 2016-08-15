package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureActivityPresent {
    /**
     * 下拉刷新
     * @param recyclerView RecyclerView
     */
    void pullToRefreshData(RecyclerView recyclerView);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page);

}
