package com.leyuan.aidong.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public class SystemBean implements Serializable {
    private List<String> open_city;     //开通城市
    private List<BannerBean> banner;    //广告位
    private ArrayList<CategoryBean> course;  //课程分类
    private ArrayList<CategoryBean> campaign;//活动分类
    private ArrayList<CategoryBean> nutrition;//营养品分类
    private ArrayList<CategoryBean> equipment;//装备分类
    private ArrayList<DistrictBean> landmark;
    private List<CategoryBean> gym_brand;   //场馆品牌

    private int order_countdown; // 订单倒计时,单位分钟
    private int appointment_countdown;// 预约倒计时,单位分钟
    private long current_time;

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

    public ArrayList<CategoryBean> getCourse() {
        return course;
    }

    public void setCourse(ArrayList<CategoryBean> course) {
        this.course = course;
    }

    public ArrayList<CategoryBean> getCampaign() {
        return campaign;
    }

    public void setCampaign(ArrayList<CategoryBean> campaign) {
        this.campaign = campaign;
    }

    public ArrayList<CategoryBean> getNutrition() {
        return nutrition;
    }

    public void setNutrition(ArrayList<CategoryBean> nutrition) {
        this.nutrition = nutrition;
    }

    public ArrayList<CategoryBean> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<CategoryBean> equipment) {
        this.equipment = equipment;
    }

    public ArrayList<DistrictBean> getLandmark() {
        return landmark;
    }

    public void setLandmark(ArrayList<DistrictBean> landmark) {
        this.landmark = landmark;
    }

    public List<CategoryBean> getGymBrand() {
        return gym_brand;
    }

    public void setGym_brand(List<CategoryBean> gym_brand) {
        this.gym_brand = gym_brand;
    }

    public int getOrder_countdown() {
        return order_countdown;
    }

    public void setOrder_countdown(int order_countdown) {
        this.order_countdown = order_countdown;
    }

    public int getAppointment_countdown() {
        return appointment_countdown;
    }

    public void setAppointment_countdown(int appointment_countdown) {
        this.appointment_countdown = appointment_countdown;
    }

    public long getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
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
                ", gym_brand=" + gym_brand +
                '}';
    }
}
