package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.FoodBean;

import java.util.List;

/**
 * 搜索场馆
 * Created by song on 2016/9/1.
 */
public interface SearchFoodFragmentView {
    /**
     * 更新列表
     * @param foodBeanList FoodBean
     */
    void updateRecyclerView(List<FoodBean> foodBeanList);


    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
