package com.leyuan.aidong.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017/7/5.
 */
public class ConfigUrlResult {

    @SerializedName("switch")
    boolean release;

    public boolean isRelease() {
        return release;
    }

    public void setRelease(boolean release) {
        this.release = release;
    }

    @Override
    public String toString() {
        return "ConfigUrlResult{" +
                "release=" + release +
                '}';
    }
}
