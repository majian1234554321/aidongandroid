package com.leyuan.aidong.entity.model;


import java.io.Serializable;

import static com.example.aidong.R.id.bmi;
import static com.example.aidong.R.id.zodiac;

public class UserCoach implements Serializable {
    private String token;
    private int id;
    private String name;
    private String avatar;
    private int gender;
    private String birthday;
    private String signature;
    private String province;
    private String city;
    private String area;
    private String mobile;
    private int height;
    private int weight;
    private String bust;
    private String waist;
    private String hip;
    private String charm_site;
    private String frequency;


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public String getCharm_site() {
        return charm_site;
    }

    public void setCharm_site(String charm_site) {
        this.charm_site = charm_site;
    }


    public String getMobile() {
        if(mobile ==null)
            return "";
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        if(name ==null)
            return "";
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }


    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserCoach{" +
                "token='" + token + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", zodiac='" + zodiac + '\'' +
                ", signature='" + signature + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", mobile='" + mobile + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", bmi=" + bmi +
                ", bust='" + bust + '\'' +
                ", waist='" + waist + '\'' +
                ", hip=" + hip +
                ", charm_site='" + charm_site + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
