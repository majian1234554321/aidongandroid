package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.course.CourseBeanNew;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/21.
 */
public interface CourseListView {
    void onGetRefreshCourseList(ArrayList<CourseBeanNew> courseList);

    void onGetMoreCourseList(ArrayList<CourseBeanNew> courselist);
}
