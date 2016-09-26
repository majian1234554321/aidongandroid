package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CourseBean;

import java.util.List;

/**
 * 课程列表
 * Created by song on 2016/8/13.
 */
public interface CoursesActivityView {

    /**
     * 更新列表
     * @param courseList
     */
    void updateRecyclerView(List<CourseBean> courseList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 隐藏空值界面
     */
    void hideEmptyView();

    /**
     * 显示RecyclerView
     */
    void showRecyclerView();

    /**
     * 隐藏RecyclerView
     */
    void hideRecyclerView();

    /**
     * 整体界面显示无网络界面
     * 对于有缓存的界面空实现该方法即可
     */
    void showErrorView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
