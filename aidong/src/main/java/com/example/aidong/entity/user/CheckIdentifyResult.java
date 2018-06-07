package com.example.aidong.entity.user;

import com.example.aidong .entity.CouponBean;
import com.example.aidong .entity.model.UserCoach;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/28.
 */
public class CheckIdentifyResult {
    UserCoach user;
    String token;

    private ArrayList<CouponBean> coupons;


    public String getToken() {
        if (token == null && user != null) {
            token = user.getToken();
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserCoach getUser() {
        return user;
    }

    public void setUser(UserCoach user) {
        this.user = user;
    }

    public ArrayList<CouponBean> getCoupons() {
        return coupons;
    }

    public void setCoupons(ArrayList<CouponBean> coupons) {
        this.coupons = coupons;
    }
}
