package com.leyuan.aidong.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 资讯
 * Created by song on 2016/11/5.
 */
public class NewsBean implements Serializable{

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("cover")
    private String cover;

    @SerializedName("published_at")
    private String datetime;

    private String titleAll;

    public NewsBean(String title, String body, String cover, String datetime, String titleAll, String id) {
        this.title = title;
        this.body = body;
        this.cover = cover;
        this.datetime = datetime;
        this.titleAll = titleAll;
        this.id = id;
    }

    public String getTitleAll() {
        return titleAll;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", cover='" + cover + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
