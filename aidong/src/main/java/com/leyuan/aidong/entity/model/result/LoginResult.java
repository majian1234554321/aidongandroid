package com.leyuan.aidong.entity.model.result;


import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;

import java.util.ArrayList;

public class LoginResult {
    private UserCoach user;
    private ArrayList<CouponBean> coupons;

    public ArrayList<CouponBean> getCoupons() {
        return coupons;
    }

    public UserCoach getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "user=" + user +
                '}';
    }

}
