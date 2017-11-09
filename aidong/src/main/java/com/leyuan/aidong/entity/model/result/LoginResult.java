package com.leyuan.aidong.entity.model.result;


import com.google.gson.JsonObject;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;

import java.util.ArrayList;

public class LoginResult {
    private UserCoach user;
    private ArrayList<CouponBean> coupons;
    private JsonObject profile_info;
    private JsonObject type;

    public ArrayList<CouponBean> getCoupons() {
        return coupons;
    }

    public UserCoach getUser() {
        return user;
    }

    public JsonObject getProfile_info() {
        return profile_info;
    }

    public void setProfile_info(JsonObject profile_info) {
        this.profile_info = profile_info;
    }

    public JsonObject getType() {
        return type;
    }

    public void setType(JsonObject type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "user=" + user +
                ", coupons=" + coupons +
                ", profile_info=" + profile_info +
                ", type=" + type +
                '}';
    }


}
