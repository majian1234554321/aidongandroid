package com.leyuan.aidong.entity.course;

import com.leyuan.aidong.entity.CoachBean;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseBeanNew {
    public static final int NORMAL = 0;
    public static final int APPOINTED = 1;
    public static final int APPOINTED_NO_PAY = 2;
    public static final int QUEUED = 3;
    public static final int FEW = 4;
    public static final int QUEUEABLE = 5;
    public static final int FULL = 6;
    public static final int END = 7;

    String id;// 课程编号,
    String name;// "课程名",
    String class_time;//  "上课时间 - 下课时间"，
    ArrayList<String> tags;//  ["标签"],
    String strength;// 强度,
    CoachBean coach;
    int reservable;// 　是否可以预约,#0-否　１-是
    String reserve_time;// "预约时间",
    int status;
    double price;//"价格
    double member_price;// "会员价格",


    ArrayList<String> image;
    Store store;//门店
    String introduce;//课程详情
    CourseSeat seat;
    private StringBuffer tagString = new StringBuffer();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public ArrayList<String> getTag() {
        return tags;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public void setTag(ArrayList<String> tag) {
        this.tags = tag;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }


    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public int getReservable() {
        return reservable;
    }

    public void setReservable(int reservable) {
        this.reservable = reservable;
    }

    public CoachBean getCoach() {
        return coach;
    }

    public void setCoach(CoachBean coach) {
        this.coach = coach;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getMember_price() {
        return member_price;
    }

    public void setMember_price(double member_price) {
        this.member_price = member_price;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public CourseSeat getSeat() {
        return seat;
    }

    public void setSeat(CourseSeat seat) {
        this.seat = seat;
    }

    public String getTags() {
        if (tagString == null && tags != null) {
            for (int i = 0; i < tags.size(); i++) {
                if (i < tags.size() - 1) {
                    tagString.append(tags.get(i)).append(" | ");
                } else {
                    tagString.append(tags.get(i));
                }
            }
        }
        return tagString.toString();
    }
}
