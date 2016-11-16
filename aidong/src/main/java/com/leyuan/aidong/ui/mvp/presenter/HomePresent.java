package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public interface HomePresent {

    /**
     * 第一次加载数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout);

    /**
     * 首页列表上拉加载更多
     * @param recyclerView 执行刷新的RecyclerView
     * @param pageSize 每页刷新的数据量
     * @param page 页码
     */
    void requestMoreHomeData(RecyclerView recyclerView, int pageSize, int page);

    /**
     * 首页列表下拉刷新
     */
    void pullToRefreshHomeData();

    /**
     * 品牌商品列表详情下拉刷新
     */
    void pullToRefreshBrandData(String id);

    /**
     * 品牌商品列表上拉加载更多
     * @param recyclerView 执行刷新的RecyclerView
     * @param pageSize 每页刷新的数据量
     * @param page 页码
     * @param id 小分类id
     */
    void requestMorBrandeData(RecyclerView recyclerView, int pageSize, int page,String id);

    /**
     * 获取Banner
     */
    void getBanners();
}
