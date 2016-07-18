package com.example.aidong.model.bean;

/**
 * 附近的人实体
 * Created by song on 2016/7/15.
 */
public class PeopleBean {
    private String name;
    private String cover;
    private String distance;
    private String attention;

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}
