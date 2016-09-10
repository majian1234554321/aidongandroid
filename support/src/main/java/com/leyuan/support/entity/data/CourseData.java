package com.leyuan.support.entity.data;

import com.leyuan.support.entity.CourseBean;

import java.util.ArrayList;

/**
 * 课程数据实体
 * Created by song on 2016/8/25.
 */
public class CourseData {
    private ArrayList<CourseBean> course;

    public ArrayList<CourseBean> getCourse() {
        return course;
    }

    public void setCourse(ArrayList<CourseBean> course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "CourseData{" +
                "course=" + course +
                '}';
    }
}