package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public interface DynamicPresent {
    /**
     * 第一次正常加载数据
     */
    void commonLoadData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新
     */
    void pullToRefreshData();

    /**
     * 上拉加载更多
     */
    void requestMoreData(RecyclerView recyclerView, int size, int page);

}
