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
    ArrayList<String> typeName = new ArrayList<>();

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
            type.add("tuition");
            type.add("free");
        }
        return type;
    }

    public ArrayList<String> getCourseTypePriceName() {

        if (typeName.isEmpty()) {
            typeName.add("全部课程");
            typeName.add("收费课程");
            typeName.add("免费课程");
        }
        return typeName;
    }

    public int getCategoryByCategoryName(String category) {
        if (all != null && category != null && all.contains(category)) {
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).equals(category))
                    return i;
            }
        }

        return 0;
    }
}
