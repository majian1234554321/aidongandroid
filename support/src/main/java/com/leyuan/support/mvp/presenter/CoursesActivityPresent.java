package com.leyuan.support.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 课程列表
 * Created by song on 2016/8/13.
 */
public interface CoursesActivityPresent {
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
}
