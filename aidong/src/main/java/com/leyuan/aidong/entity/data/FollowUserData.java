package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.UserBean;

import java.util.List;

/**
 * Created by user on 2018/2/2.
 */
public class FollowUserData {

    private List<UserBean> followings;
    private List<UserBean> coach;

    public List<UserBean> getUser() {
        return followings;
    }

    public List<UserBean> getCoach() {
        return coach;
    }

    public List<UserBean> getFollowings() {
        return followings;
    }
}
