package com.example.aidong.entity.data;

import com.example.aidong .entity.UserBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 关注
 * Created by song on 2016/9/10.
 */
public class FollowData implements Serializable {

    private List<UserBean> followings;
    private List<UserBean> user;
    public ArrayList<String> following_ids;

    public List<UserBean> getFollow() {
        return followings;
    }

    public void setFollower(List<UserBean> follower) {
        this.followings = follower;
    }

    public List<UserBean> getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "FollowData{" +
                "follower=" + followings +
                '}';
    }

    public static boolean isFollow(String id, ArrayList<String> followList) {
        boolean isFollow = false;
        if (followList == null) return false;
        for (String ids : followList) {
            if (ids.equals(id)) {
                isFollow = true;
                break;
            }
        }
        return isFollow;
    }
}
