package com.leyuan.aidong.entity.course;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseFilterBean {
    ArrayList<CourseBrand> company;
    ArrayList<CourseName> course;

    public ArrayList<CourseName> getCourse() {
        return course;
    }

    public void setCourse(ArrayList<CourseName> course) {
        this.course = course;
    }

    public ArrayList<CourseBrand> getCompany() {
        return company;
    }

    public void setCompany(ArrayList<CourseBrand> company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "CourseFilterBean{" +
                "company=" + company +
                ", course=" + course +
                '}';
    }
}
