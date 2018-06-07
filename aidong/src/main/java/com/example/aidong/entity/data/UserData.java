package com.example.aidong.entity.data;

import com.example.aidong .entity.UserBean;

import java.util.List;

/**
 * 用户
 * Created by song on 2016/9/18.
 */
public class UserData {

    private List<UserBean> user;
    private List<UserBean> coach;

    public List<UserBean> getCoach() {
        return coach;
    }

    public List<UserBean> getUser() {
        return user;
    }


    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "user=" + user +
                '}';
    }
}
