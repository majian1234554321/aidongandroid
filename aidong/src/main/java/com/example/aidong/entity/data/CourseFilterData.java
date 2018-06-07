package com.example.aidong.entity.data;

import com.example.aidong .entity.course.CourseFilterBean;

/**
 * Created by user on 2017/11/20.
 */
public class CourseFilterData {
    CourseFilterBean config;

    public CourseFilterBean getFilter() {
        return config;
    }

    public void setFilter(CourseFilterBean filter) {
        this.config = filter;
    }
}
