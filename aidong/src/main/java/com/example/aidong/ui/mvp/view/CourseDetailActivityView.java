package com.example.aidong.ui.mvp.view;

import com.example.aidong.entity.CourseDetailBean;

/**
 * 课程详情
 * Created by song on 2016/8/17.
 */
public interface CourseDetailActivityView {

    /**
     * 获取课程详情
     * @param courseDetailBean 课程详情实体
     */
    void setCourseDetail(CourseDetailBean courseDetailBean);

    /**
     * 显示无网络界面
     */
    void showErrorView();
}
