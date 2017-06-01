package com.leyuan.aidong.entity;

import com.leyuan.aidong.utils.constant.PayType;

import java.util.List;

/**
 * 提交服务器生成订单实体
 * Created by song on 2016/11/14.
 */

public class PayOrderBean {
    private String id;
    private String no;
    private String total;
    private String pay_type;
    private String pay_amount;
    private String status;
    private List<String> item;
    private PayOptionBean pay_option;

    /********
     * 订单详情
     ************/
    private String coin;
    private String integral;
    private String coupon;
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @PayType
    public String getPayType() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPayAmount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }

    public PayOptionBean getpayOption() {
        return pay_option;
    }

    public void setPay_option(PayOptionBean pay_option) {
        this.pay_option = pay_option;
    }

    @Override
    public String toString() {
        return "PayOrderBean{" +
                "no='" + no + '\'' +
                ", total='" + total + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", pay_amount='" + pay_amount + '\'' +
                ", status='" + status + '\'' +
                ", item=" + item +
                ", pay_option=" + pay_option +
                '}';
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}