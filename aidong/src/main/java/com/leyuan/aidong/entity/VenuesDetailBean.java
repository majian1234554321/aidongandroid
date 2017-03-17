package com.leyuan.aidong.entity;

import com.google.gson.annotations.SerializedName;

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

    private String price;
    private double distance;
    private double lat;
    private double lng;
    private int gyms_count;


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

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
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
}
