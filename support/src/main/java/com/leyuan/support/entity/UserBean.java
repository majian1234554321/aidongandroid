package com.leyuan.support.entity;

/**
 * 用户
 * Created by song on 2016/8/2.
 */
public class UserBean {
    //发现-人
    private String id;          //编号
    private String name;        //名字
    private String avatar;      //头像
    private String gender;      //性别
    private String distance;    //距离

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
