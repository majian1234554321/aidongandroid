package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.EquipmentBean;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .entity.NurtureBean;

import java.util.List;

/**
 * 营养品和装备筛选
 * Created by song on 2016/11/27.
 */
public interface GoodsFilterActivityView {

    /**
     * 更新列表
     * @param beanList
     */
    void updateNurtureRecyclerView(List<NurtureBean> beanList);
    void updateEquipmentRecyclerView(List<EquipmentBean> beanList);
    void updateGoodsRecyclerView(List<GoodsBean> beanList);
    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();


    void showEmptyView();
}
