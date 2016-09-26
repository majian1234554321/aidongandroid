package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

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
    void commonLoadCampaignData(SwitcherLayout switcherLayout, String keyword);

    void commonLoadCourseData(SwitcherLayout switcherLayout, String keyword);

    void commonLoadFoodData(SwitcherLayout switcherLayout, String keyword);

    void commonUserData(SwitcherLayout switcherLayout, String keyword);

    void commonLoadVenuesData(SwitcherLayout switcherLayout, String keyword);

    /**
     * 下拉刷新
     * @param keyword 关键字
     */
    void pullToRefreshCampaignData(String keyword);

    void pullToRefreshCourseData(String keyword);

    void pullToRefreshFoodData(String keyword);

    void pullToRefreshUserData(String keyword);

    void pullToRefreshVenuesData(String keyword);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param keyword 关键字
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreCampaignData(RecyclerView recyclerView, String keyword, int pageSize, int page);

    void requestMoreCourseData(RecyclerView recyclerView, String keyword, int pageSize, int page);

    void requestMoreFoodData(RecyclerView recyclerView, String keyword, int pageSize, int page);

    void requestMoreUserData(RecyclerView recyclerView, String keyword, int pageSize, int page);

    void requestMoreVenuesData(RecyclerView recyclerView, String keyword, int pageSize, int page);
}
