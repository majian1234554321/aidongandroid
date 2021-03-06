package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.FoodAndVenuesBean;

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
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
