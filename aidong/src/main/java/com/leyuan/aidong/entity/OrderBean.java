package com.leyuan.aidong.entity;

import java.util.ArrayList;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public class OrderBean {
    private String id;                      //订单号
    private String total;                   //应付款
    private String pay_type;                //支付方式 alipay-支付宝 wxpay-微信
    private String pay_amount;              //实付款
    private String status;                  //订单状态 0-未支付 1-已支付 2-已发货 3-已确认收货 4-已评论
    private ArrayList<ParcelBean> parcel;   //商品实体
    private String created_at;
    private boolean is_food;                // 是否营养餐

    public String getCreated_at() {
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPay_type() {
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

    public ArrayList<ParcelBean> getParcel() {
        return parcel;
    }

    public void setParcel(ArrayList<ParcelBean> parcel) {
        this.parcel = parcel;
    }

    public boolean is_food() {
        return is_food;
    }

    public void setIs_food(boolean is_food) {
        this.is_food = is_food;
    }
}
