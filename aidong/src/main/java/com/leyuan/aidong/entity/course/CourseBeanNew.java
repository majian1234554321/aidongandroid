package com.leyuan.aidong.entity.course;

import com.leyuan.aidong.entity.CoachBean;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseBeanNew {
    String id;// 课程编号,
    String name;// "课程名",
    String class_time;//  "上课时间 - 下课时间"，
    ArrayList<String> tag;//  ["标签"],
    String strength;// 强度,
    String coach_name;// "教练名",
    String coach_avatar;// "教练头像",
    int reservable;// 　是否可以预约,#0-否　１-是
    String reserve_time;// "预约时间",
    boolean has_queued;// 是否已经预约,#true是　false-否
    boolean has_appointed;// 是否已经预约,#true是　false-否


    //详情
    CoachBean coach;
    double price;//"价格

    double member_price;// "会员价格",

    Store store;//门店

    String introduce;//课程详情
//    boolean reservable;// 是否需要预约，
//    String reserve_time;//开始预约时间,
   CourseSeat seat;

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
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getCoach_name() {
        return coach_name;
    }

    public void setCoach_name(String coach_name) {
        this.coach_name = coach_name;
    }

    public String getCoach_avatar() {
        return coach_avatar;
    }

    public void setCoach_avatar(String coach_avatar) {
        this.coach_avatar = coach_avatar;
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

    public boolean isHas_queued() {
        return has_queued;
    }

    public void setHas_queued(boolean has_queued) {
        this.has_queued = has_queued;
    }

    public CoachBean getCoach() {
        return coach;
    }

    public void setCoach(CoachBean coach) {
        this.coach = coach;
    }

    public boolean isHas_appointed() {
        return has_appointed;
    }

    public void setHas_appointed(boolean has_appointed) {
        this.has_appointed = has_appointed;
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
}
