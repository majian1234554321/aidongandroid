package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.List;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public interface EquipmentPresent {
    /**
     * 设置装备分类信息
     * @param beanList List<CategoryBean>
     */
    void setCategory(List<CategoryBean> beanList);

    /**
     * 第一次进入界面加载装备列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新装备列表数据
     */
    void pullToRefreshData();

    /**
     * 上拉加载更多装备列表数据
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page);

}
