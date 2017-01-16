package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.ProfileBean;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class UserInfoData {

    private String id;
    private ProfileBean profile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }
}
