package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 商品 包含装备、健康餐饮、营养品
 * Created by song on 2016/9/22.
 */
public interface GoodsDetailPresent {


    /**
     * 获取商品详情
     * @param type 装备:equipment 健康餐饮:food 营养品 nurture
     * @param id  装备 健康餐饮 营养品 id
     */
    void getGoodsDetail(String type,String id);

    /**
     * 获取商品详情
     * @param switcherLayout SwitcherLayout
     * @param type 装备:equipment 健康餐饮:food 营养品 nurture
     * @param id  装备 健康餐饮 营养品 id
     */
    void getGoodsDetail(SwitcherLayout switcherLayout,String type,String id);

    /**
     * 第一次加载装备的自提场馆
     * @param switcherLayout SwitcherLayout
     * @param type 装备:equipment 健康餐饮:food 营养品 nurture
     * @param sku Sku码
     */
    void commonLoadVenues(SwitcherLayout switcherLayout,String type,String sku);

    /**
     * 下拉刷新装备的自提场馆
     * @param type  装备:equipment 健康餐饮:food 营养品 nurture
     * @param sku Sku码
     */
    void pullToRefreshVenues(String type,String sku);


    /**
     * 上拉加载更多装备的自提场馆
     * @param recyclerView  RecyclerView
     * @param type  装备:equipment 健康餐饮:food 营养品 nurture
     * @param sku Sku码
     * @param page 页码
     */
    void requestMoreVenues(RecyclerView recyclerView, final int pageSize, String type, String sku, int page);

}
