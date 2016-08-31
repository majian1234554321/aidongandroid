package com.leyuan.support.entity;

import java.util.ArrayList;

/**
 * 活动详情
 * Created by song on 2016/8/2.
 */
public class VenuesDetailBean {
    private String id;
    private String name;
    private String logo;
    private ArrayList<String> photo;
    private String impressions;
    private String address;
    private String business_time;
    private ArrayList<String> service;

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
