package com.leyuan.aidong.entity.user;

import com.leyuan.aidong.entity.VenuesBean;

import java.util.List;

/**
 * Created by user on 2017/3/23.
 */
public class MineInfoBean {

    int followings_count; //关注的人的数量
    int followers_count;//粉丝数量

    int appointed_count;//已付款预约
    int appointing_count; //未付款预约
    int paid_orders_count; //已付款订单数量
    int unpay_orders_count;//未付款订单数量
    int orders_count;//订单总数量
    int cart_items_count;//购物车数量
    String views_count;//热度

    private String acivity;

    public int getOrders_count() {
        return orders_count;
    }

    public void setOrders_count(int orders_count) {
        this.orders_count = orders_count;
    }

    private List<VenuesBean> gyms;  //运动足记

    public int getAppointed_count() {
        return appointed_count;
    }

    public void setAppointed_count(int appointed_count) {
        this.appointed_count = appointed_count;
    }

    public int getAppointing_count() {
        return appointing_count;
    }

    public void setAppointing_count(int appointing_count) {
        this.appointing_count = appointing_count;
    }

    public int getPaid_orders_count() {
        return paid_orders_count;
    }

    public void setPaid_orders_count(int paid_orders_count) {
        this.paid_orders_count = paid_orders_count;
    }

    public int getUnpay_orders_count() {
        return unpay_orders_count;
    }

    public void setUnpay_orders_count(int unpay_orders_count) {
        this.unpay_orders_count = unpay_orders_count;
    }

    public String getView_count() {
        return views_count;
    }

    public void setView_count(String view_count) {
        this.views_count = view_count;
    }

    public int getFollowings_count() {
        return followings_count;
    }

    public void setFollowings_count(int followings_count) {
        this.followings_count = followings_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getCart_items_count() {
        return cart_items_count;
    }

    public void setCart_items_count(int cart_items_count) {
        this.cart_items_count = cart_items_count;
    }


    public List<VenuesBean> getGyms() {
        return gyms;
    }

    public void setGyms(List<VenuesBean> gyms) {
        this.gyms = gyms;
    }

    public String getActivity() {
        return acivity;
    }

    public void setActivity(String activity) {
        this.acivity = activity;
    }
}
