package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 订单详情
 * Created by song on 2016/9/1.
 */
public class OrderDetailBean {
    private String id;              //订单号
    private String status;
    private String coin;
    private String coupon;
    private String integral;
    private String pay_amount;
    private String total;
    private String created_at;
    private PayOrderBean pay_option;
    private List<ParcelBean> parcel;

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getPayAmount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public PayOrderBean getPay_option() {
        return pay_option;
    }

    public void setPay_option(PayOrderBean pay_option) {
        this.pay_option = pay_option;
    }

    public List<ParcelBean> getParcel() {
        return parcel;
    }

    public void setParcel(List<ParcelBean> parcel) {
        this.parcel = parcel;
    }
}
