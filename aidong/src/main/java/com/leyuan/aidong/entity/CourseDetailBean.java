package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 课程详情实体
 * Created by song on 2016/8/13.
 */
public class CourseDetailBean {
    private String code;        //课程编号
    private String name;        //课程名称
    private String cover;       //封面
    private String class_date;       //上课日期
    private String class_time;       //上课时间
    private String break_time;       //下课时间
    private List<CoachBean> coach;   //课程
    private List<VenuesBean> gym;    //场馆
    private String place;            //名额
    private String applied_count;    //已报名人数
    private List<UserBean> applied;  //报名的人
    private String introduce;        //课程介绍

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getClass_date() {
        return class_date;
    }

    public void setClass_date(String class_date) {
        this.class_date = class_date;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getBreak_time() {
        return break_time;
    }

    public void setBreak_time(String break_time) {
        this.break_time = break_time;
    }

    public List<CoachBean> getCoach() {
        return coach;
    }

    public void setCoach(List<CoachBean> coach) {
        this.coach = coach;
    }

    public List<VenuesBean> getGym() {
        return gym;
    }

    public void setGym(List<VenuesBean> gym) {
        this.gym = gym;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getApplied_count() {
        return applied_count;
    }

    public void setApplied_count(String applied_count) {
        this.applied_count = applied_count;
    }

    public List<UserBean> getApplied() {
        return applied;
    }

    public void setApplied(List<UserBean> applied) {
        this.applied = applied;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "CourseDetailBean{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", class_date='" + class_date + '\'' +
                ", class_time='" + class_time + '\'' +
                ", break_time='" + break_time + '\'' +
                ", coach=" + coach +
                ", gym=" + gym +
                ", place='" + place + '\'' +
                ", applied_count='" + applied_count + '\'' +
                ", applied=" + applied +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
