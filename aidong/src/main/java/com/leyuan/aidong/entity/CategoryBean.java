package com.leyuan.aidong.entity;

/**
 * 营养品，装备分类实体
 * Created by song on 2016/8/18.
 */
public class CategoryBean {
    private String id;
    private String name;    //分类名称
    private String image;   //分类图标

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
