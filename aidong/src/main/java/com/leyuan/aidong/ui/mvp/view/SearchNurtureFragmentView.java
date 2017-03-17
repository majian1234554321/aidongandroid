package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.NurtureBean;

import java.util.List;

/**
 * 搜索营养品
 * Created by song on 2016/9/1.
 */
public interface SearchNurtureFragmentView {
    /**
     * 更新列表
     * @param foodBeanList FoodBean
     */
    void updateRecyclerView(List<NurtureBean> foodBeanList);


    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
