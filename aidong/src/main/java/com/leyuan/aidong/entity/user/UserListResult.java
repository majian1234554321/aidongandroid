package com.leyuan.aidong.entity.user;

import com.leyuan.aidong.entity.model.UserCoach;

import java.util.List;

/**
 * Created by user on 2017/3/4.
 */
public class UserListResult {

    List<UserCoach> profile;

    public List<UserCoach> getProfile() {
        return profile;
    }

    public void setProfile(List<UserCoach> profile) {
        this.profile = profile;
    }
}
