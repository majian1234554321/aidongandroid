package com.leyuan.aidong.ui.mvp.presenter;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartActivityPresent {

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
    void deleteGoods(String ids);

    /**
     * 更新商品数量
     * @param id 商品id
     * @param mount 数量
     */
    void updateGoods(String id,int mount);
}
