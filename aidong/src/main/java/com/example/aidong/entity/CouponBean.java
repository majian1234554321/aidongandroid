package com.example.aidong.entity;

import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponBean {
    private String coupon_id;           //优惠券编号
    private String name;                //优惠券名字
    private String discount;            //抵扣金额
    private String min;                 //订单最小金额可用
    private String start_date;          //有效期-开始时间
    private String end_date;            //有效期－结束时间
    private String use_date;            //使用时间
    private List<String> desc;          //优惠劵展开描述信息


    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getUse_date() {
        return use_date;
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "CouponBean{" +
                "coupon_id='" + coupon_id + '\'' +
                ", name='" + name + '\'' +
                ", discount='" + discount + '\'' +
                ", min='" + min + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", use_date='" + use_date + '\'' +
                '}';
    }
}
