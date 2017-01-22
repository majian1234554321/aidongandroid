package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 推荐
 * Created by song on 2017/1/19.
 */
public interface RecommendPresent {

    /**
     * 第一次进入界面列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commendLoadData(SwitcherLayout switcherLayout, String type);

    /**
     * 下拉刷新列表数据
     */
    void pullToRefreshData(String type);

    /**
     * 上拉加载更多列表数据
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page,String type);

}
