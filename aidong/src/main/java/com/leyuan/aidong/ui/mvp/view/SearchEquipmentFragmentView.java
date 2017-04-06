package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.EquipmentBean;

import java.util.List;

/**
 * 搜索装备
 * Created by song on 2016/9/1.
 */
public interface SearchEquipmentFragmentView {



    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void onRecyclerViewRefresh(List<EquipmentBean> equipmentBeanList);

    void onRecyclerViewLoadMore(List<EquipmentBean> equipmentBeanList);
}
