package com.example.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.example.aidong .widget.SwitcherLayout;

/**
 * 发现
 * Created by song on 2016/8/29.
 */
public interface DiscoverPresent {
    /**
     * 发现首页
     * @param switcherLayout
     */
    void commonLoadDiscoverData(SwitcherLayout switcherLayout);
    void pullToRefreshDiscoverData();


    /**
     * 第一次正常加载数据
     */
    void commonLoadUserData(SwitcherLayout switcherLayout,double lat, double lng, String gender, String type);
    void commonLoadNewsData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新
     */
    void pullToRefreshUserData(double lat, double lng, String gender, String type);
    void pullToRefreshNewsData();

    /**
     * 上拉加载更多
     */
    void requestMoreUserData(RecyclerView recyclerView, double lat, double lng, String gender, String type,int size, int page);
    void requestMoreNewsData(RecyclerView recyclerView,int pageSize,int page);

    /**
     * 添加关注
     * @param id
     */
    void addFollow(String id);

    /**
     * 取消关注
     * @param id
     */
    void cancelFollow(String id);

    void addFollow(String id, String type);

    void cancelFollow(String id, String type);
}
