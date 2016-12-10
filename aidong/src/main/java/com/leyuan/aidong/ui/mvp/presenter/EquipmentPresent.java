package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public interface EquipmentPresent {
    /**
     * 设置装备分类信息
     */
    void getCategory();

    /**
     * 第一次进入界面加载推荐列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadRecommendData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新推荐列表数据
     */
    void pullToRefreshRecommendData();

    /**
     * 上拉加载更多推荐列表数据
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreRecommendData(RecyclerView recyclerView, int pageSize, int page);

    /**
     * 第一次进入界面加载装备列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadEquipmentData(SwitcherLayout switcherLayout,String brandId,
                                 String priceSort, String countSort, String heatSort);

    /**
     * 下拉刷新装备列表数据
     */
    void pullToRefreshEquipmentData(String brandId,String priceSort, String countSort, String heatSort);


    /**
     * 上拉加载更多装备列表数据
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreEquipmentData(RecyclerView recyclerView, int pageSize, int page,String brandId,
                                  String priceSort, String countSort, String heatSort);

}
