package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.UserBean;

import java.util.List;

/**
 * 关注
 * Created by song on 2016/9/10.
 */
public class FollowData {

    private List<UserBean> user;

    public List<UserBean> getFollow() {
        return user;
    }

    public void setFollower(List<UserBean> follower) {
        this.user = follower;
    }

    @Override
    public String toString() {
        return "FollowData{" +
                "follower=" + user +
                '}';
    }
}
