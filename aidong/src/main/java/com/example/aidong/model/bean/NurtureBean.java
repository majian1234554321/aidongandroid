package com.example.aidong.model.bean;

/**
 * 营养品实体
 * Created by song on 2016/7/16.
 */
public class NurtureBean {
    private String id;              //营养品ID
    private String name;            //营养品名字
    private String cover;           //营养品封面
    private String price;           //售价
    private String market_price;    //市场价

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
}
