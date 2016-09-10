package com.leyuan.support.entity.data;

import com.leyuan.support.entity.UserBean;

import java.util.List;

/**
 * 我关注的人
 * Created by song on 2016/9/10.
 */
public class FollowingData {

    private List<UserBean> following;

    public List<UserBean> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserBean> following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "FollowingData{" +
                "following=" + following +
                '}';
    }
}
