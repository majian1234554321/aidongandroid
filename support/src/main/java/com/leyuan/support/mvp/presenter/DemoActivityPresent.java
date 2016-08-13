package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by song on 2016/8/9.
 */
public interface DemoActivityPresent {
    /**
     * 下拉刷新
     */
    void pullToRefreshData(RecyclerView recyclerView);


    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param pageSize 每次请求的数据量
     * @param page 第几页
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page);

}
