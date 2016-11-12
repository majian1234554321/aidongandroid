package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurturePresent {
    /**
     * 获取营养品分类信息
     * @return List<CategoryBean>
     */
    void getCategory();

    /**
     * 第一次进入界面加载营养品列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout);

    /**
     * 下拉刷新营养品列表数据
     */
    void pullToRefreshData();

    /**
     * 上拉加载更多营养品列表数据
     * @param recyclerView RecyclerView
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page);

}
