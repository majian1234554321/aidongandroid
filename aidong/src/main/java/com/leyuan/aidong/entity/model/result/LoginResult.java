package com.leyuan.aidong.entity.model.result;


import com.leyuan.aidong.entity.model.UserCoach;

public class LoginResult {
    private UserCoach user;

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
