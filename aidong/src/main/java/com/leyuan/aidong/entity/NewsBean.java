package com.leyuan.aidong.entity;

/**
 * 资讯
 * Created by song on 2016/11/5.
 */
public class NewsBean {
    private String id;
    private String title;
    private String body;
    private String cover;
    private String datetime;

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
