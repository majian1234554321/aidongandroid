package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public class SystemBean implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.open_city);
        dest.writeList(this.banner);
        dest.writeList(this.course);
        dest.writeList(this.campaign);
        dest.writeList(this.nutrition);
        dest.writeList(this.equipment);
        dest.writeList(this.landmark);
    }

    public SystemBean() {
    }

    protected SystemBean(Parcel in) {
        this.open_city = in.createStringArrayList();
        this.banner = new ArrayList<BannerBean>();
        in.readList(this.banner, BannerBean.class.getClassLoader());
        this.course = new ArrayList<CategoryBean>();
        in.readList(this.course, CategoryBean.class.getClassLoader());
        this.campaign = new ArrayList<CategoryBean>();
        in.readList(this.campaign, CategoryBean.class.getClassLoader());
        this.nutrition = new ArrayList<CategoryBean>();
        in.readList(this.nutrition, CategoryBean.class.getClassLoader());
        this.equipment = new ArrayList<CategoryBean>();
        in.readList(this.equipment, CategoryBean.class.getClassLoader());
        this.landmark = new ArrayList<BusinessCircleBean>();
        in.readList(this.landmark, BusinessCircleBean.class.getClassLoader());
    }

    public static final Creator<SystemBean> CREATOR = new Creator<SystemBean>() {
        @Override
        public SystemBean createFromParcel(Parcel source) {
            return new SystemBean(source);
        }

        @Override
        public SystemBean[] newArray(int size) {
            return new SystemBean[size];
        }
    };
}
