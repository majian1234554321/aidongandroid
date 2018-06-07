package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public interface EquipmentActivityView{


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
