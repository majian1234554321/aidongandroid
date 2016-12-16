package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 场馆
 * Created by song on 2016/9/21.
 */
public interface VenuesPresent {

    /**
     * 获取场馆品牌列表
     */
    void getGymBrand();

    /**
     * 获取商圈信息
     */
    void getBusinessCircle();

    /**
     * 第一次正常加载场馆列表数据
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout);


    /**
     * 下拉刷新场馆列表数据
     */
    void pullToRefreshData();

    /**
     * 上拉加载更多场馆列表数据
     * @param recyclerView RecyclerView
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView,int pageSize, int page);

    /**
     * 获取场馆详情
     * @param id 场馆id
     */
    void getVenuesDetail(SwitcherLayout switcherLayout,String id);

    /**
     * 获取场馆课程列表
     * @param id 场馆id
     */
    void getCourses(SwitcherLayout switcherLayout,String id);

    /**
     * 获取场馆教练列表
     * @param id 场馆id
     */
    void getCoaches(SwitcherLayout switcherLayout,String id);
}
