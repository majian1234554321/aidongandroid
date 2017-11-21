package com.leyuan.aidong.entity.course;

/**
 * Created by user on 2017/11/20.
 */
public class CourseStore {

    String id;// 门店编号
    String name;// "门店名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CourseStore{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
