package com.example.aidong.entity.course;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseBrand {

    String id;
    String name;
    ArrayList<CourseArea> area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CourseArea> getArea() {
        return area;
    }

    public void setArea(ArrayList<CourseArea> area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "CourseBrand{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", area=" + area +
                '}';
    }




}
