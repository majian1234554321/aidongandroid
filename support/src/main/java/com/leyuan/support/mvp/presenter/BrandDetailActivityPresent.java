package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 品牌详情
 * Created by song on 2016/8/13.
 */
public interface BrandDetailActivityPresent {

    /**
     * 下拉刷新
     */
    void pullToRefreshData(int id);


    /**
     * 上拉加载更多
     * @param recyclerView 执行刷新的RecyclerView
     * @param pageSize 每页刷新的数据量
     * @param page 页码
     * @param id 小分类id
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page,int id);

}
