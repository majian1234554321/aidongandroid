package com.leyuan.aidong.entity;

/**
 * 营养品类型实体
 * Created by song on 2016/8/18.
 */
public class CategoryBean {
    private String cover;
    private String type;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "cover='" + cover + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
