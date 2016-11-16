package com.leyuan.aidong.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动详情实体
 * Created by song on 2016/8/18.
 */
public class CampaignDetailBean {
    private String campaign_id;             //活动编号
    private String name;                    //活动名称
    private List image;                     //活动封面图
    private String landmark;                //活动地标
    private String start_time;              //活动开始时间
    private String end_time;                //活动结束时间
    private String address;                 //活动举办地址
    private String organizer;               //活动举办者
    private String place;                   //活动名额
    private String introduce;               //活动介绍
    private String price;                   //活动价格
    private String market_price;            //活动指导价
    private String enroll_start_time;       //报名开始时间
    private String enroll_end_time;         //报名截至时间
    private ArrayList<Applicant> applicant; //报名的人

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getImage() {
        return image;
    }

    public void setImage(List image) {
        this.image = image;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getEnroll_start_time() {
        return enroll_start_time;
    }

    public void setEnroll_start_time(String enroll_start_time) {
        this.enroll_start_time = enroll_start_time;
    }

    public String getEnroll_end_time() {
        return enroll_end_time;
    }

    public void setEnroll_end_time(String enroll_end_time) {
        this.enroll_end_time = enroll_end_time;
    }

    public ArrayList<Applicant> getApplicant() {
        return applicant;
    }

    public void setApplicant(ArrayList<Applicant> applicant) {
        this.applicant = applicant;
    }

    public class Applicant{
        private String idong_id;
        private String name;
        private String avatar;

        public String getIdong_id() {
            return idong_id;
        }

        public void setIdong_id(String idong_id) {
            this.idong_id = idong_id;
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

        @Override
        public String toString() {
            return "Applicant{" +
                    "idong_id='" + idong_id + '\'' +
                    ", name='" + name + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CampaignDetailBean{" +
                "campaign_id='" + campaign_id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", landmark='" + landmark + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", address='" + address + '\'' +
                ", organizer='" + organizer + '\'' +
                ", place='" + place + '\'' +
                ", introduce='" + introduce + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", enroll_start_time='" + enroll_start_time + '\'' +
                ", enroll_end_time='" + enroll_end_time + '\'' +
                ", applicant=" + applicant +
                '}';
    }
}
