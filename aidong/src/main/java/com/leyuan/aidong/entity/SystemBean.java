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
    private ArrayList<CategoryBean> foods;//营养餐分类

    private ArrayList<CategoryBean> equipment;//装备分类
    private ArrayList<DistrictBean> landmark; //商圈
    private List<CategoryBean> gym_brand;   //场馆品牌
    private ArrayList<String> gym_types;

    private int order_countdown; // 订单倒计时,单位分钟
    private int appointment_countdown;// 预约倒计时,单位分钟
    private long current_time;
    private String acivity;     //课程视频底部提示文字(未登录状态下)
    private double express_price;    //快递费

    private String limit_period; //截止时间段,

    private int limit_days; //限制购买天数,
    private ArrayList<String> periods; //可选时间段

    public String getLimit_period() {
        return limit_period;
    }

    public void setLimit_period(String limit_period) {
        this.limit_period = limit_period;
    }

    public int getLimit_days() {
        return limit_days;
    }

    public void setLimit_days(int limit_days) {
        this.limit_days = limit_days;
    }

    public ArrayList<String> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<String> periods) {
        this.periods = periods;
    }

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

    public ArrayList<CategoryBean> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<CategoryBean> foods) {
        this.foods = foods;
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


    public String getAcivity() {
        return acivity;
    }


    public void setAcivity(String acivity) {
        this.acivity = acivity;
    }

    public ArrayList<String> getGymTypes() {
        return gym_types;
    }

    public void setGym_types(ArrayList<String> gym_types) {
        this.gym_types = gym_types;
    }

    public double getExpressPrice() {
        return express_price;
    }

    public void setExpressPrice(double expressPrice) {
        this.express_price = expressPrice;
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
