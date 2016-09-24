package com.example.aidong.entity;

import java.util.List;

/**
 * 购物车中商家实体
 * Created by song on 2016/9/23.
 */
public class ShopBean {

    private String shopname;
    private String opentime;
    private List<GoodsBean> item;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public List<GoodsBean> getItem() {
        return item;
    }

    public void setItem(List<GoodsBean> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "ShopBean{" +
                "shopname='" + shopname + '\'' +
                ", opentime='" + opentime + '\'' +
                ", item=" + item +
                '}';
    }
}
