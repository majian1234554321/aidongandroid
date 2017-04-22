package com.leyuan.aidong.entity;

/**
 * Created by user on 2017/3/6.
 */
public class VersionInformation {

    int id;

    String version;//: 版本


    boolean update_force;

//    int isUpdate;//: 是否强制更新（0否，1是）

    String apk_url;//：下载地址（安卓）

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
        return update_force;
    }

    public void setUpdate_force(boolean update_force) {
        this.update_force = update_force;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }
}
