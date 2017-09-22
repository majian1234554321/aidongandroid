package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 推荐
 * Created by song on 2017/1/19.
 */
public interface RecommendPresent {

    /**
     * 第一次进入界面列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commendLoadRecommendData(SwitcherLayout switcherLayout, String type);

    /**
     * 下拉刷新列表数据
     */
    void pullToRefreshRecommendData( String type);

    /**
     * 上拉加载更多列表数据
     */
    void requestMoreRecommendData(RecyclerView recyclerView, int pageSize, int page,
                                   String type);

}
