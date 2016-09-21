package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 搜索
 * Created by song on 2016/9/18.
 */
public interface SearchPresent {

    /**
     * 第一次加载数据
     * @param switcherLayout SwitcherLayout
     * @param keyword 关键字
     */
    void commonLoadData(SwitcherLayout switcherLayout, String keyword);

    /**
     * 下拉刷新
     * @param keyword 关键字
     */
    void pullToRefreshData(String keyword);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param keyword 关键字
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, String keyword, int pageSize, int page);
}
