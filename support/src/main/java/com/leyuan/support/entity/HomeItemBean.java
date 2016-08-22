package com.leyuan.support.entity;

import java.util.ArrayList;

/**
 * 首页item实体
 * Created by song on 2016/7/14.
 */
public class HomeItemBean {

    private ArrayList<HomeCategoryBean> category; //所有样式及数据集合
    private String name;                      //大分类名字
    private String display;                   //展示样式类型

    public ArrayList<HomeCategoryBean> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<HomeCategoryBean> category) {
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
        return "HomeItemBean{" +
                "category=" + category +
                ", name='" + name + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
