package com.example.aidong.model;

import org.json.JSONObject;

/**
 * Created by user on 2015/6/19.
 */
public class PrivateEducation {

    String photoUrl;
    String price;
    String coachId;
    String chName;
    String enName;
    String sex;

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public void parseJaon(JSONObject jsonObject) {
        photoUrl = jsonObject.optString("photoUrl");
        price = jsonObject.optString("price");
        coachId = jsonObject.optString("coachId");
        chName = jsonObject.optString("chName");
        enName = jsonObject.optString("enName");
        sex = jsonObject.optString("sex");
    }
}
