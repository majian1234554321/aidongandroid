package com.example.aidong.entity.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017/3/10.
 */
public class SystemMessageInfo {


    @SerializedName("tip")
    String hint;

    String time;
    @SerializedName("title")
    String type;
    @SerializedName("content")
    String content;
    @SerializedName("link")
    String url;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
