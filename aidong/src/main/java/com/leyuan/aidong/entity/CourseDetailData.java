package com.leyuan.aidong.entity;


@Deprecated
public class CourseDetailData {
    private CourseDetailBean course;

    public CourseDetailBean getCourse() {
        return course;
    }

    public void setCourse(CourseDetailBean course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "CourseDetailData{" +
                "course=" + course +
                '}';
    }
}
