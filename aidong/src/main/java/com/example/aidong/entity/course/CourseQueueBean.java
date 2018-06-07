package com.example.aidong.entity.course;

/**
 * Created by user on 2017/11/23.
 */
public class CourseQueueBean {

    public static final String canceled = "canceled";
    public static final String queued = "queued";
    public static final String appointed = "appointed";
    public static final String suspended = "suspended";

    String id;// "预约编号"
    CourseBeanNew timetable;
    CourseStore store;

    String status;//排队状态: canceled-已取消 queued-已排队 appointed-已预约 suspended-已听课
    int position;

    String coupon;
    String created_at;
    String mobile;
    String pay_amount;
    String total;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CourseBeanNew getTimetable() {
        return timetable;
    }

    public void setTimetable(CourseBeanNew timetable) {
        this.timetable = timetable;
    }

    public CourseStore getStore() {
        return store;
    }

    public void setStore(CourseStore store) {
        this.store = store;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
