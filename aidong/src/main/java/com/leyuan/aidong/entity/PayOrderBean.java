package com.leyuan.aidong.entity;

/**
 * 提交服务器生成订单实体
 * Created by song on 2016/11/14.
 */
public class PayOrderBean {
    private String item_id;         //商品规格
    private String amount;          //购买数量
    private String coupon;          //优惠券号
    private String integral;        //积分
    private String pay_type;        //支付方式
    private String contact_name;    //联系人
    private String contact_mobile;  //联系电话

    private String request;		//签名后的支付宝请求数据

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
