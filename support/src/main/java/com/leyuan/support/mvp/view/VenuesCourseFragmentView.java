package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.CourseBean;

import java.util.List;

/**
 * 场馆详情 - 课程
 * Created by song on 2016/8/30.
 */
public interface VenuesCourseFragmentView {

    /**
     * 设置课程列表
     * @param courseBeanList 课程集合
     */
    void setCourses(List<CourseBean> courseBeanList);


}
