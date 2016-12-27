package com.leyuan.aidong.ui.mvp.presenter;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartPresent {

    /**
     * 第一次正常加载数据
     * @param switcherLayout
     */
    void normalLoadingData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新
     */
    void pullToRefreshData();


    /**
     * 删除购物车中商品
     * @param ids 商品id 多个以逗号隔开
     */
    void deleteCart(String ids);

    /**
     * 更新商品数量
     * @param id 商品id
     * @param mount 数量
     */
    void updateCart(String id, int mount);

    /**
     * 添加商品到购物车
     * @param skuCode 商品sku码
     * @param amount 数量
     */
    void addCart(String skuCode, int amount,String gymId);
}
