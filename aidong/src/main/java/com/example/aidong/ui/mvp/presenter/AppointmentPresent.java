package com.example.aidong.ui.mvp.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.ShareData;
import com.example.aidong .widget.SwitcherLayout;

/**
 * 预约
 * Created by song on 2016/9/1.
 */
public interface AppointmentPresent {

    /**
     * 第一次加载
     * @param type 全部 已参加 未参加
     */
    void commonLoadData( String type);

    /**
     * 下拉刷新
     * @param type 全部 已参加 未参加
     */
    void pullToRefreshData(SwipeRefreshLayout refreshLayout,String type);

    /**
     * 上拉加载更多
     * @param recyclerView RecyclerView
     * @param type 全部 已参加 未参加
     * @param pageSize 每页加载数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, String type, int pageSize, int page);

    /**
     * 获取预约详情
     * @param switcherLayout SwitcherLayout
     * @param id 预约id
     */
    void getAppointmentDetail(SwitcherLayout switcherLayout, String id);

    /**
     * 取消预约
     * @param id
     */
    void cancelAppoint(String id);

    /**
     * 确认预约
     * @param id
     */
    void confirmAppoint(String id);

    /**
     * 删除预约
     * @param id
     */
    void deleteAppoint(String id);

    /**
     * 获取课程预约详情
     * @param id 课程id
     */
    void getCourseAppointDetail(SwitcherLayout switcherLayout,String id);

    /**
     * 获取活动预约详情
     * @param id 活动id
     */
    void getCampaignAppointDetail(SwitcherLayout switcherLayout,String id);

    ShareData.ShareCouponInfo getShareInfo();
}
