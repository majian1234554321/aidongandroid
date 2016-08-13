package com.leyuan.support.entity;

/**
 * Created by pc on 2016/8/2.
 */
public class VenuesBean {
    private String id;              //场馆编号
    private String name;            //场馆名字
    private String cover;           //场馆封面
    private String address;         //场馆地址
    private String distance;        //距离
    private String coaches_count;   //私教数量
    private String courses_count;   //课程数量
    private String price;           //价格

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCoaches_count() {
        return coaches_count;
    }

    public void setCoaches_count(String coaches_count) {
        this.coaches_count = coaches_count;
    }

    public String getCourses_count() {
        return courses_count;
    }

    public void setCourses_count(String courses_count) {
        this.courses_count = courses_count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
