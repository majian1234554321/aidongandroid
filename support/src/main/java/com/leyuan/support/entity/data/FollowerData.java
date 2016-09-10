package com.leyuan.support.entity.data;

import com.leyuan.support.entity.UserBean;

import java.util.List;

/**
 * 关注我的人
 * Created by song on 2016/9/10.
 */
public class FollowerData {

    private List<UserBean> follower;

    public List<UserBean> getFollower() {
        return follower;
    }

    public void setFollower(List<UserBean> follower) {
        this.follower = follower;
    }

    @Override
    public String toString() {
        return "FollowerData{" +
                "follower=" + follower +
                '}';
    }
}
