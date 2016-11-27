package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.NurtureBean;

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
    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
