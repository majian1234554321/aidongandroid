package com.leyuan.aidong.entity;

import java.util.ArrayList;

/**
 * 健康餐饮实体
 * Created by song on 2016/8/2.
 */
public class FoodBean {

    private String food_id;         //商品编号
    private String name;            //名字
    private String cover;           //封面
    private String price;           //售价
    private String market_price;    //市场价
    private ArrayList<String> material;        //主要食材
    private ArrayList<String> crowd;           //推荐人群

    public String getFoodId() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
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


    public ArrayList<String> getMaterial() {
        return material;
    }

    public void setMaterial(ArrayList<String> material) {
        this.material = material;
    }

    public ArrayList<String> getCrowd() {
        return crowd;
    }

    public void setCrowd(ArrayList<String> crowd) {
        this.crowd = crowd;
    }

    @Override
    public String toString() {
        return "FoodBean{" +
                "food_id='" + food_id + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", material=" + material +
                ", crowd=" + crowd +
                '}';
    }
}
