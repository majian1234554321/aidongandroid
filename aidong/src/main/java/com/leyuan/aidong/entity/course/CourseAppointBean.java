package com.leyuan.aidong.entity.course;

import com.leyuan.aidong.entity.PayOptionBean;

/**
 * Created by user on 2017/11/23.
 */
public class CourseAppointBean {

    private static final String CANCELED = "canceled";
    public static final String HISTORY = "history";
    public static final String QUEUED = "queued";
    public static final String APPOINTED = "appointed";


    //订单状态
    public static final String pending = "pending";
    public static final String paid = "paid";
    public static final String refunded = "refunded";
    // 排队状态
    public static final String canceled = "canceled";
    public static final String queued = "queued";
    public static final String appointed = "appointed";
    public static final String suspended = "suspended";
    // 预约状态+上面
    public static final String absent = "absent";
    public static final String signed = "signed";


    String no;// '订单号'
    String created_at;
    String id;// "预约编号"
    String code;//: "核销码",
    CourseBeanNew timetable;
    CourseStore store;
    String introduce;
    String seat;
    PayOptionBean payment;
    String pay_amount; //付款金额
    String order_status; //订单状态: canceled-已取消，pending-待支付，paid-已支付，refunded-已退款

    String position;//排在第几位

    String status;// 排队状态: canceled-已取消, queued-正在排队,appointed-已预约,suspended-已停课
    // 预约状态: canceled-已取消，appointed-已预约，absent-缺席，signed-已签到，suspended-已停课


    public String getIntroduce() {
        return introduce;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public PayOptionBean getPayment() {
        return payment;
    }

    public void setPayment(PayOptionBean payment) {
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFinalStatus() {
//        if (order_status == null) {
//            return status;
//        } else
        if (pending.equals(order_status) || paid.equals(order_status)) {
            return order_status;
        } else {
            return status;
        }
    }

    public String getCourseFinalStatus() {
//        if (order_status == null) {
//            return status;
//        } else
        if (pending.equals(order_status)) {
            return order_status;
        } else {
            return status;
        }
    }

    public String getQueueFinalStatus() {
        return status;
    }

}
