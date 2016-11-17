package com.leyuan.aidong.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public class SystemBean implements Serializable{
    private List<String> open_city;     //开通城市
    private List<BannerBean> banner;    //广告位
    private List<CategoryBean> course;  //课程分类
    private List<CategoryBean> campaign;//活动分类
    private List<CategoryBean> nutrition;//营养品分类
    private List<CategoryBean> equipment;//装备分类
    private List<BusinessCircleBean> landmark;

    public List<String> getOpen_city() {
        return open_city;
    }

    public void setOpen_city(List<String> open_city) {
        this.open_city = open_city;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<CategoryBean> getCourse() {
        return course;
    }

    public void setCourse(List<CategoryBean> course) {
        this.course = course;
    }

    public List<CategoryBean> getCampaign() {
        return campaign;
    }

    public void setCampaign(List<CategoryBean> campaign) {
        this.campaign = campaign;
    }

    public List<CategoryBean> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<CategoryBean> nutrition) {
        this.nutrition = nutrition;
    }

    public List<CategoryBean> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<CategoryBean> equipment) {
        this.equipment = equipment;
    }

    public List<BusinessCircleBean> getLandmark() {
        return landmark;
    }

    public void setLandmark(List<BusinessCircleBean> landmark) {
        this.landmark = landmark;
    }

    @Override
    public String toString() {
        return "SystemBean{" +
                "open_city=" + open_city +
                ", banner=" + banner +
                ", course=" + course +
                ", campaign=" + campaign +
                ", nutrition=" + nutrition +
                ", equipment=" + equipment +
                ", landmark=" + landmark +
                '}';
    }
}
