package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 课程列表
 * Created by song on 2016/8/13.
 */
public interface CoursePresent {
    /**
     * 获取时间
     */
    void getDate();

    /**
     * 获取课程分类
     */
    void getCategory();

    /**
     * 获取商圈信息
     */
    void getBusinessCircle();

    /**
     * 第一次加载数据
     */
    void commendLoadData();

    /**
     * 下拉刷新
     * @param category 课程类型
     * @param day 从当前开始向后天数
     */
    void pullToRefreshData(int category, int day);


    /**
     * 上拉加载更多
     * @param recyclerView 执行刷新的RecyclerView
     * @param pageSize 每页刷新的数据量
     * @param category 课程类型
     * @param day 从当前开始向后天数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, int category, int day, int page);

    /**
     * 获取课程详情
     * @param id 课程id
     */
    void getCourseDetail(String id);
}
