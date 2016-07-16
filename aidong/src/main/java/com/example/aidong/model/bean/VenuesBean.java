package com.example.aidong.model.bean;

/**
 * 场馆实体
 * Created by song on 2016/7/15.
 */
public class VenuesBean {
    private String id;              //id
    private String name;            //名字
    private String logo;            //场馆logo
    private String cover;           //场馆照片封面
    private String courses_count;    //课程数量
    private String coaches_count;   //教练数量
    private String address;         //地址
    private String distance;        //距离
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCourses_count() {
        return courses_count;
    }

    public void setCourses_count(String courses_coun) {
        this.courses_count = courses_coun;
    }

    public String getCoaches_count() {
        return coaches_count;
    }

    public void setCoaches_count(String coaches_count) {
        this.coaches_count = coaches_count;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
