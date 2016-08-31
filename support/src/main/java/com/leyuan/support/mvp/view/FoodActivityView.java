package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.FoodAndVenuesBean;

/**
 * 健康餐饮
 * Created by song on 2016/8/15.
 */
public interface FoodActivityView {
    /**
     * 更新列表
     * @param foodAndVenuesBean 健康餐饮界面实体
     */
    void updateRecyclerView(FoodAndVenuesBean foodAndVenuesBean);


    /**
     * 整体界面显示无网络界面
     * 对于有缓存的界面空实现该方法即可
     */
    void showErrorView();


    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
