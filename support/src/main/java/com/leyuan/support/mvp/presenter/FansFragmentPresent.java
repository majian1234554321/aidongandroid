package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 粉丝
 * Created by song on 2016/8/19.
 */
public interface FansFragmentPresent {

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

    /**
     * 添加关注
     * @param id user id
     */
    void addFollow(int id);

    /**
     * 取消关注
     * @param id user id
     */
    void cancelFollow(int id);
}
