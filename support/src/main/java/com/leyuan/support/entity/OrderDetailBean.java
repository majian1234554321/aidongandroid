package com.leyuan.support.entity;

/**
 * 订单详情
 * Created by song on 2016/9/1.
 */
public class OrderDetailBean {
    private String id;              //订单号
    private String total;           //应付款
    private String coupon;          //优惠券优惠金额
    private String integral;        //积分优惠金额
    private String promotion;       //活动优惠
    private String freight;         //运费
    private String pay_type;        //支付方式
    private String pay_amount;      //实付款
    private String status;          //订单状态 0-未支付 1-已支付 2-已发货 3-已确认收货 4-已评论
    private GoodsBean item;         //商品


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_amount() {
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

    public GoodsBean getItem() {
        return item;
    }

    public void setItem(GoodsBean item) {
        this.item = item;
    }
}
