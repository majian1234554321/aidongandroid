package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.entity.ProfileBean;

import java.util.List;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class UserInfoData {

    private List<ImageBean> photowall;
    private ProfileBean profile;

    public List<ImageBean> getPhotoWall() {
        return photowall;
    }

    public void setPhotowall(List<ImageBean> photoWall) {
        this.photowall = photoWall;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }
}
