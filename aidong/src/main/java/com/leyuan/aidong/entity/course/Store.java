package com.leyuan.aidong.entity.course;

/**
 * Created by user on 2017/11/20.
 */
public class Store {

    String name;// "门店",
    String address;// "地址",
    double[] coordinate;// [经度,维度],
    String classroom;// "教室"

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public  double[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate( double[] coordinate) {
        this.coordinate = coordinate;
    }
}
