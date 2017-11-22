package com.leyuan.aidong.entity.course;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseName {
    ArrayList<String> free;
    ArrayList<String> tuition;
    ArrayList<String> all;
    ArrayList<String> type = new ArrayList<>();

    public ArrayList<String> getFree() {
        return free;
    }

    public void setFree(ArrayList<String> free) {
        this.free = free;
    }

    public ArrayList<String> getTuition() {
        return tuition;
    }

    public void setTuition(ArrayList<String> tuition) {
        this.tuition = tuition;
    }

    public ArrayList<String> getAll() {
        return all;
    }

    public void setAll(ArrayList<String> all) {
        this.all = all;
    }

    public ArrayList<String> getCourseTypePrice() {
        if (type.isEmpty()) {
            type.add("all");
            type.add("free");
            type.add("tuition");
        }
        return type;
    }
}
