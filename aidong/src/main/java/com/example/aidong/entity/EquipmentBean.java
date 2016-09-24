package com.example.aidong.entity;

/**
 * 装备
 * Created by song on 2016/8/23.
 */
public class EquipmentBean {
    private String id;
    private String name;
    private String cover;
    private String price;
    private String market_price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    @Override
    public String toString() {
        return "EquipmentBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                '}';
    }
}
