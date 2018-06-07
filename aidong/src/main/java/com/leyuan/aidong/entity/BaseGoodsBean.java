package com.leyuan.aidong.entity;

import android.content.Context;

import com.example.aidong.R;

import static com.leyuan.aidong.utils.Constant.GOODS_EQUIPMENT;
import static com.leyuan.aidong.utils.Constant.GOODS_FOODS;
import static com.leyuan.aidong.utils.Constant.GOODS_TICKET;

/**
 * Created by user on 2017/8/1.
 */
public class BaseGoodsBean {

    protected GoodsType goodsType;

    protected String id;
    protected String name;
    protected String cover;
    protected String price;
    protected String market_price;
    protected String brand_name;
    protected String floor_price;

    public String getFloor_price() {
        return floor_price;
    }

    public void setFloor_price(String floor_price) {
        this.floor_price = floor_price;
    }

    public String getBrandName() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

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

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }


    /**
     * 要修改
     */
    public static String getGoodsNameByType(Context context, GoodsType goodsType) {
        return goodsType == BaseGoodsBean.GoodsType.NURTURE ? context.getString(R.string.home_nurture) :
                goodsType == BaseGoodsBean.GoodsType.FOODS ? context.getString(R.string.home_food) :
                        goodsType == GoodsType.TICKET ? context.getString(R.string.ticket_competition)
                                : context.getString(R.string.home_equipment);
    }

    /**
     * 要修改
     */
    public static String getGoodsNameByType(Context context, String goodsType) {
        return GOODS_EQUIPMENT.equals(goodsType) ? context.getString(R.string.home_equipment) :
                GOODS_FOODS.equals(goodsType) ? context.getString(R.string.home_food) :
                        GOODS_TICKET.equals(goodsType) ? context.getString(R.string.ticket_competition)
                                : context.getString(R.string.home_nurture);
    }

    public enum GoodsType {
        FOODS, NURTURE, TICKET, EQUIPMENT
    }

}
