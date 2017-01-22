package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurturePresent {
    /**
     * 获取营养品分类信息
     */
    void getCategory();

    /**
     * 第一次进入界面加载营养品推荐列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadRecommendData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新营养品推荐列表数据
     */
    void pullToRefreshRecommendData();

    /**
     * 上拉加载更多营养品推荐列表数据
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreRecommendData(RecyclerView recyclerView, int pageSize, int page);

    /**
     * 第一次进入界面加载营养品列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commendLoadNurtureData(SwitcherLayout switcherLayout, String brandId,
                                String priceSort, String countSort, String heatSort);

    /**
     * 下拉刷新营养品列表数据
     */
    void pullToRefreshNurtureData(String brandId,String priceSort, String countSort, String heatSort);

    /**
     * 上拉加载更多营养品列表数据
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreNurtureData(RecyclerView recyclerView, int pageSize, int page, String brandId,
                                String priceSort, String countSort, String heatSort);

    /**
     * 立即购买
     * @param skuCode
     * @param amount
     * @param pickUp
     * @param pickUpId
     */
    void buyNurtureImmediately(String skuCode,int amount,String pickUp,String pickUpId);
}
