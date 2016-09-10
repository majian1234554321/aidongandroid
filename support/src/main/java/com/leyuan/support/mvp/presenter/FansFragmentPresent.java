package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 粉丝
 * Created by song on 2016/8/19.
 */
public interface FansFragmentPresent {

    /**
     * 第一次正常加载数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新
     */
    void pullToRefreshData();

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
