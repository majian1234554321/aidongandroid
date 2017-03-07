package com.leyuan.aidong.entity;

/**
 * 课程实体
 * Created by song on 2016/8/2.
 */
public class CourseBean {
    private String code;
    private String name;
    private String cover;
    private String price;
    private String market_price;
    private String place;
    private String applied_count;
    private String class_time;
    private String break_time;
    private String address;
    private String distance;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAppliedCount() {
        return applied_count;
    }

    public void setApplied_count(String applied_count) {
        this.applied_count = applied_count;
    }

    public String getClassTime() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getBreakTime() {
        return break_time;
    }

    public void setBreak_time(String break_time) {
        this.break_time = break_time;
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

    @Override
    public String toString() {
        return "CourseBean{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", place='" + place + '\'' +
                ", applied_count='" + applied_count + '\'' +
                ", class_time='" + class_time + '\'' +
                ", break_time='" + break_time + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
