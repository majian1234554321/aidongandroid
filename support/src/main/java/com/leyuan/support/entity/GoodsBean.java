package com.leyuan.support.entity;

/**
 * 首页商品实体
 * Created by song on 2016/7/14.
 */
public class GoodsBean {
    private String sku_code;      //商品编码
    private  String cover;         //商品封面
    private  String name;          //商品名字
    private  String price;         //商品售价
    private  String  market_price; //商品市场价

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
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
        return "GoodsBean{" +
                "sku_code='" + sku_code + '\'' +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                '}';
    }
}
