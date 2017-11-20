package com.leyuan.aidong.entity.course;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseArea {

    String name;
    ArrayList<CourseStore> store;

    public ArrayList<CourseStore> getStore() {
        return store;
    }

    public void setStore(ArrayList<CourseStore> store) {
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
