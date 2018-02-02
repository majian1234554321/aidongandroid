package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 关注
 * Created by song on 2016/8/19.
 */
public interface FollowPresent {


    void getFollowList();

    /**
     * 第一次加载数据
     * @param switcherLayout SwitcherLayout
     * @param type type followings:我关注的人 followers:关注我的人
     */
    void commonLoadData(SwitcherLayout switcherLayout,String type);

    /**
     * 下拉刷新
     * @param type type followings:我关注的人 followers:关注我的人
     */
    void pullToRefreshData(String type);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param type type followings:我关注的人 followers:关注我的人
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize,String type, int page);

    /**
     * 添加关注
     * @param id user id
     */
    void addFollow(String id);

    /**
     * 取消关注
     * @param id user id
     */
    void cancelFollow(String id);
}
