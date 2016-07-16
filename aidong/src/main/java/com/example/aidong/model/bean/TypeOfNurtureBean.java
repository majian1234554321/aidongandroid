package com.example.aidong.model.bean;

/**
 * 营养品类型实体
 * Created by song on 2016/7/16.
 */
public class TypeOfNurtureBean {
    private String id;              //营养品类型ID
    private String name;            //营养品类型名字
    private String cover;           //营养品类型封面

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
