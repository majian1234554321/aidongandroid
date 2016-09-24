package com.example.aidong.entity;

import java.util.ArrayList;

/**
 * 首页实体
 * Created by song on 2016/8/23.
 */
public class HomeBean {

    private ArrayList<HomeItemBean> category; //所有样式及数据集合
    private String name;                      //大分类名字
    private String display;                   //展示样式类型

    public ArrayList<HomeItemBean> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<HomeItemBean> category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "category=" + category +
                ", name='" + name + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
