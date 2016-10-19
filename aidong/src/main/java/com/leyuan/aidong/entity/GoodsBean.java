package com.leyuan.aidong.entity;

import java.util.ArrayList;

/**
 * 商品实体 当前包含营养品、 健康餐饮、 装备
 * Created by song on 2016/7/14.
 */
public class GoodsBean {
    private  String sku_code;      //商品编码
    private  String cover;         //商品封面
    private  String name;          //商品名字
    private  String price;         //商品售价
    private  String  market_price; //商品市场价

    /******订单商品中需要用到的字段******/
    private ArrayList<String> spec_name;
    private ArrayList<String> spec_value;
    private String amount;

    private boolean checked = false;        //标记是否被选中

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

    public ArrayList<String> getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(ArrayList<String> spec_name) {
        this.spec_name = spec_name;
    }

    public ArrayList<String> getSpec_value() {
        return spec_value;
    }

    public void setSpec_value(ArrayList<String> spec_value) {
        this.spec_value = spec_value;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "sku_code='" + sku_code + '\'' +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", spec_name=" + spec_name +
                ", spec_value=" + spec_value +
                ", amount='" + amount + '\'' +
                ", checked=" + checked +
                '}';
    }
}
