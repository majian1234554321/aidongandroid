package com.example.aidong.entity.data;

import com.example.aidong .entity.CourseBean;

import java.util.ArrayList;

/**
 * 课程数据实体
 * Created by song on 2016/8/25.
 */
@Deprecated
public class CourseData {
    private String date;
    private ArrayList<CourseBean> course;

    public ArrayList<CourseBean> getCourse() {
        return course;
    }

    public void setCourse(ArrayList<CourseBean> course) {
        this.course = course;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CourseData{" +
                "course=" + course +
                '}';
    }
}
