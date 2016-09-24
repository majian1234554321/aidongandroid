package com.example.aidong.entity.data;

import com.example.aidong.entity.CouponBean;

import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/9/14.
 */
public class CouponData {
    private List<CouponBean> coupon;

    public List<CouponBean> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<CouponBean> coupon) {
        this.coupon = coupon;
    }

    @Override
    public String toString() {
        return "CouponData{" +
                "coupon=" + coupon +
                '}';
    }
}
