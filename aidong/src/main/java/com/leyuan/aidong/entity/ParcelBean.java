package com.leyuan.aidong.entity;

import android.text.TextUtils;

import com.leyuan.aidong.utils.constant.DeliveryType;

import java.util.List;

/**
 * 订单包裹
 * Created by song on 2017/3/23.
 */
public class ParcelBean {
    private String id;
    private String order_id;
    private String name;
    private String address;
    private String mobile;
    private String remark;
    private String pick_up_way;     // 取货类型,#0-快递 1-自提
    private String pick_up_id;      // 自提门店编号
    private String pick_up_name;    // 自提门店名称
    private String pick_up_date;    // 自提日期
    private String pick_up_status;  // 包裹状态
    private String pick_up_period;

    private String verify_no;  // "核销码",
    private String verify_status;  // "核销状态 undo-未核销 done-已核销",

    private List<GoodsBean> item;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

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

    public String getVerify_no() {
        return verify_no;
    }

    public void setVerify_no(String verify_no) {
        this.verify_no = verify_no;
    }

    public String getVerify_status() {
        return verify_status;
    }

    public void setVerify_status(String verify_status) {
        this.verify_status = verify_status;
    }

    @DeliveryType
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

    public String getPick_up_period() {
        return pick_up_period;
    }

    public void setPick_up_period(String pick_up_period) {
        this.pick_up_period = pick_up_period;
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

    public boolean isVerified() {

        return TextUtils.equals(verify_status,"done");
    }
}
