package com.leyuan.aidong.entity;

/**
 * 首页item实体
 * Created by song on 2016/8/23.
 */
public class HomeItemBean{
    private String id;              //商品编号
    private String name;            //商品名
    private String cover;           //封面图片
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

    @Override
    public String toString() {
        return "HomeItemBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                '}';
    }
}
