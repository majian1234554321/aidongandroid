package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 订单包裹
 * Created by song on 2017/3/23.
 */
public class ParcelBean {
    private String name;
    private String address;
    private String mobile;
    private String remark;
    private String pick_up_way;     // 取货类型,#0-快递 1-自提
    private String pick_up_id;      // 自提门店编号
    private String pick_up_name;    // 自提门店名称
    private String pick_up_date;    // 自提日期
    private String pick_up_status;  // 包裹状态

    private List<GoodsBean> item;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPickUpWay() {
        return pick_up_way;
    }

    public void setPick_up_way(String pick_up_way) {
        this.pick_up_way = pick_up_way;
    }

    public String getPick_up_id() {
        return pick_up_id;
    }

    public void setPick_up_id(String pick_up_id) {
        this.pick_up_id = pick_up_id;
    }

    public String getPickUpName() {
        return pick_up_name;
    }

    public void setPick_up_name(String pick_up_name) {
        this.pick_up_name = pick_up_name;
    }

    public String getPickUpDate() {
        return pick_up_date;
    }

    public void setPick_up_date(String pick_up_date) {
        this.pick_up_date = pick_up_date;
    }

    public String getPick_up_status() {
        return pick_up_status;
    }

    public void setPick_up_status(String pick_up_status) {
        this.pick_up_status = pick_up_status;
    }

    public List<GoodsBean> getItem() {
        return item;
    }

    public void setItem(List<GoodsBean> item) {
        this.item = item;
    }
}