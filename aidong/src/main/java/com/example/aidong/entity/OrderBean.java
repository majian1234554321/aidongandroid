package com.example.aidong.entity;

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
    private ArrayList<GoodsBean> item;      //商品实体

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

    public ArrayList<GoodsBean> getItem() {
        return item;
    }

    public void setItem(ArrayList<GoodsBean> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "id='" + id + '\'' +
                ", total='" + total + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", pay_amount='" + pay_amount + '\'' +
                ", status='" + status + '\'' +
                ", item=" + item +
                '}';
    }
}
