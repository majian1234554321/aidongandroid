package com.leyuan.aidong.entity.course;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseFilterBean {
    ArrayList<CourseBrand> company;
    CourseName course;
    ArrayList<CourseBrand> mine;

    public CourseName getCourse() {
        return course;
    }

    public void setCourse(CourseName course) {
        this.course = course;
    }

    public CourseBrand getMine() {
        if (mine != null && !mine.isEmpty()) {
            return mine.get(0);
        }
        return null;
    }

    @Deprecated
    public void setMine(CourseBrand mine) {
        if (this.mine == null) {
            this.mine = new ArrayList<>();
        }
        this.mine.clear();
        this.mine.add(mine);
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
