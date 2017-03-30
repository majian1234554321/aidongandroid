package com.leyuan.aidong.entity.user;

import com.leyuan.aidong.entity.model.UserCoach;

/**
 * Created by user on 2017/3/28.
 */
public class CheckIdentifyResult {
    UserCoach user;
    String token;


    public String getToken() {
        if (token == null && user != null) {
            token = user.getToken();
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
