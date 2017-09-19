package com.leyuan.aidong.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017/3/6.
 */
public class VersionInformation {

    int id;

    @SerializedName("version")
    String version;//: 版本

    @SerializedName("isUpdate")
    int isUpdate;//: 是否强制更新（0否，1是）

    boolean update_force;

    @SerializedName("apk")
    String apk_url;//：下载地址（安卓）

    String image;//封面

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUpdate_force() {
        return isUpdate == 1;
    }


    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
