package com.leyuan.aidong.entity;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.leyuan.aidong.entity.model.Coordinate;

import java.util.ArrayList;

/**
 * 场馆详情
 * Created by song on 2016/8/2.
 */
public class VenuesDetailBean {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;
    @SerializedName("image")
    private ArrayList<String> photo;

    @SerializedName("impressions")
    private String impressions;

    @SerializedName("address")
    private String address;
    @SerializedName("business_time")
    private String business_time;

    @SerializedName("service")
    private ArrayList<String> service;
    @SerializedName("tel")
    private String tel;

    @SerializedName("area")
    private String area;
    @SerializedName("price")
    private String price;
    @SerializedName("distance")
    private double distance;
    @SerializedName("coordinate")
    private Coordinate coordinate;
    private int gyms_count;

    @SerializedName("introduce")
    private String introduce;

    @SerializedName("landmark")
    private String landmark;

    @SerializedName("brother")
    private ArrayList<VenuesBean> brother;


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

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }

    public String getImpressions() {
        return impressions;
    }

    public void setImpressions(String impressions) {
        this.impressions = impressions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_time() {
        return business_time;
    }

    public void setBusiness_time(String business_time) {
        this.business_time = business_time;
    }

    public ArrayList<String> getService() {
        return service;
    }

    public void setService(ArrayList<String> service) {
        this.service = service;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public ArrayList<VenuesBean> getBrother() {
        return brother;
    }

    public void setBrother(ArrayList<VenuesBean> brother) {
        this.brother = brother;
    }

    public int getGyms_count() {
        return gyms_count;
    }

    public void setGyms_count(int gyms_count) {
        this.gyms_count = gyms_count;
    }

    @Override
    public String toString() {
        return "VenuesDetailBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", photo=" + photo +
                ", impressions='" + impressions + '\'' +
                ", address='" + address + '\'' +
                ", business_time='" + business_time + '\'' +
                ", service=" + service +
                '}';
    }

    @SuppressLint("DefaultLocale")
    public String getDistanceFormat() {
        if (getDistance() < 100)
            return "小于100m";
        return String.format("%.2f", (getDistance() / 1000)) + "km";
    }
}
