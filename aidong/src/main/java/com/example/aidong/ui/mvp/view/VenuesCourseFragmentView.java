package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CourseBean;
import com.example.aidong .entity.data.CourseData;

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


    void onGetCoursesFirst(CourseData courseData);

    void showEmptyView();
}
