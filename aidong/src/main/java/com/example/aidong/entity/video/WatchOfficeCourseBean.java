package com.example.aidong.entity.video;


import com.google.gson.annotations.SerializedName;

/**
 * 视界模块相关课程实体
 * Created song pc on 2016/7/25.
 */
public class WatchOfficeCourseBean {

    @SerializedName("cover")
    private String conUrl;
    @SerializedName("id")
    private String conID;

    @SerializedName("name")
    private String dictName;

    public String getConUrl() {
        return conUrl;
    }

    public void setConUrl(String conUrl) {
        this.conUrl = conUrl;
    }

    public String getConID() {
        return conID;
    }

    public void setConID(String conID) {
        this.conID = conID;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
}
