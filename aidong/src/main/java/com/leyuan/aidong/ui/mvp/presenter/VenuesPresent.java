package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.SwitcherLayout;

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
     * 获取场馆分类
     */
    void getGymTypes();

    /**
     * 第一次正常加载场馆列表数据
     *
     * @param switcherLayout SwitcherLayout
     */
    void commonLoadData(SwitcherLayout switcherLayout, String brand_id, String landmark,String area,String gymTypes);

    /**
     * 下拉刷新场馆列表数据
     */
    void pullToRefreshData(String brand_id, String landmark,String area,String gymTypes);

    /**
     * 上拉加载更多场馆列表数据
     *
     * @param recyclerView RecyclerView
     * @param page         页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int page, String brand_id, String landmark,String area,String gymTypes);

    /**
     * 获取场馆详情
     *
     * @param id 场馆id
     */
    void getVenuesDetail(SwitcherLayout switcherLayout, String id);

    void getVenuesDetail(String id);

    /**
     * 获取场馆课程列表
     *
     * @param id 场馆id
     * @day 0..6#0表示今天，1表示明天，依次类推
     */
    void getCourses(SwitcherLayout switcherLayout, String id, String day);

    /**
     * 获取场馆教练列表
     *
     * @param id 场馆id
     */
    void getCoaches(SwitcherLayout switcherLayout, String id);

    /**
     * 预约场馆
     *
     * @param id     场馆id
     * @param date   日期
     * @param period 时间段
     * @param name   用户名
     * @param mobile 手机
     */
    void appointVenues(String id, String date, String period, String name, String mobile);


    /**
     * 预约私教
     *
     * @param id      场馆id
     * @param coachId 教练id
     * @param date    日期
     * @param period  时间段
     * @param name    用户名
     * @param mobile  手机
     */
    void appointCoach(String id, String coachId, String date, String period, String name, String mobile);

    void getCoursesFirst(SwitcherLayout switcherLayout, String id);
}
