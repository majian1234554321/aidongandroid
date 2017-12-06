package com.leyuan.aidong.entity.course;

import android.text.TextUtils;

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

    public String getStoreByVenuesBean(String brand_name, String name) {
        if (company != null) {
            for (CourseBrand courseBrand : company) {
                if (TextUtils.equals(courseBrand.getName(), brand_name)) {
                    for (CourseArea courseArea : courseBrand.getArea()) {
                        for (CourseStore courseStore : courseArea.getStore()) {
                            if (TextUtils.equals(courseStore.getName(), name)) {
                                return courseBrand.getId() + "," + courseArea.getName() + "," + courseStore.getId();
                            }
                        }
                    }
                }
            }
        }

        return "-1,-1,-1";
    }
}
