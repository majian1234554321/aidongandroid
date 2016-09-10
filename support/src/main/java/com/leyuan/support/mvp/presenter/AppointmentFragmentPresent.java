package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 预约
 * Created by song on 2016/9/1.
 */
public interface AppointmentFragmentPresent {

    /**
     * 第一次加载
     * @param switcherLayout SwitcherLayout
     * @param type 全部 已参加 未参加
     */
    void commonLoadData(SwitcherLayout switcherLayout, String type);

    /**
     * 下拉刷新
     * @param type 全部 已参加 未参加
     */
    void pullToRefreshData(String type);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param type 全部 已参加 未参加
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, String type, int pageSize, int page);
}