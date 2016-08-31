package com.leyuan.support.entity;

/**
 * Created by song on 2016/8/30.
 */
public class ImageBean {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "url='" + url + '\'' +
                '}';
    }
}
