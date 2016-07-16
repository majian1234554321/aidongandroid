package com.example.aidong.model.bean;

import java.util.ArrayList;

/**
 * 首页item实体
 * Created by song on 2016/7/15.
 */
public class HomeItemBean {
    private String id;           //小分类编号
    private String type;         // 小分类类型
    private String cover;        // 封面
    private ArrayList<GoodsBean> item;   //推荐列表商品

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public ArrayList<GoodsBean> getItem() {
        return item;
    }

    public void setItem(ArrayList<GoodsBean> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "HomeItemBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", cover='" + cover + '\'' +
                ", item=" + item +
                '}';
    }
}
