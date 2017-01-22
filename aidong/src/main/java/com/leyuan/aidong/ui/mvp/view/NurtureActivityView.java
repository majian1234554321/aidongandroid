package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureActivityView {
    /**
     * 设置营养品的分类信息
     * @param categoryBeanList CategoryBean
     */
    void setCategory(ArrayList<CategoryBean> categoryBeanList);

    /**
     * 更新列表
     * @param goodsBeanList
     */
    void updateRecyclerView(List<GoodsBean> goodsBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void showEmptyView();
}
