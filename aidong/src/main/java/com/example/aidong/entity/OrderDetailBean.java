package com.example.aidong.entity;

import com.example.aidong .utils.Constant;

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
    private PayOptionBean pay_option;
    private List<ParcelBean> parcel;
    private String pay_type;
    private String express_price;
    private String order_type;
    private boolean is_virtual;

    public boolean is_virtual() {
        return is_virtual;
    }

    public String getPayType() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

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

    public PayOptionBean getPay_option() {
        return pay_option;
    }

    public void setPay_option(PayOptionBean pay_option) {
        this.pay_option = pay_option;
    }

    public List<ParcelBean> getParcel() {
        return parcel;
    }

    public void setParcel(List<ParcelBean> parcel) {
        this.parcel = parcel;
    }

    public String getExpressPrice() {
        return express_price;
    }

    public void setExpress_price(String express_price) {
        this.express_price = express_price;
    }

    public boolean is_food() {
        return Constant.GOODS_FOODS.equals(order_type);
    }
}
