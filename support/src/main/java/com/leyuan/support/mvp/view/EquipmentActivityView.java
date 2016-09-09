package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.EquipmentBean;

import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public interface EquipmentActivityView{

    /**
     * 更新列表
     * @param equipmentBeanList 装备列表实体数据集合
     */
    void updateRecyclerView(List<EquipmentBean> equipmentBeanList);

    /**
     * 显示装备列表空值界面
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
