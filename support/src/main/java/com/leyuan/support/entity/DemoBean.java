package com.leyuan.support.entity;

/**
 * Created by song on 2016/8/9.
 */
public class DemoBean {
    private String _id;
    private String content;
    private String pulishedAt;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPulishedAt() {
        return pulishedAt;
    }

    public void setPulishedAt(String pulishedAt) {
        this.pulishedAt = pulishedAt;
    }
}
