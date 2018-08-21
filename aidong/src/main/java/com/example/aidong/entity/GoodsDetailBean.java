package com.example.aidong.entity;

import java.util.List;

/**
 * 营养品，健康餐饮，装备商品详情
 * Created by song on 2016/9/22.
 */
public class GoodsDetailBean {
    public String id;          //商品编号
    public String name;             //商品名称
    public List<String> image;      //图片地址
    public String price;            //商品价格
    public String floor_price;
    public String market_price;     //商品市场价
    public boolean is_virtual; //是否虚拟


    public String discount;

    public String recommend_coach;

    public String introduce;        //商品更多详情
    public String question;
    public String service;
    public GoodsSpecBean spec;       //商品规格
    public List<CouponBean> coupon; //商品优惠券
    public DeliveryBean pick_up;
    private String goodsType;


    @Override
    public String toString() {
        return "GoodsDetailBean{" +
                "food_id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", introduce='" + introduce + '\'' +
                ", spec=" + spec +
                ", coupon=" + coupon +
                '}';
    }

    public static String getRealGoodsType(String goodsType, String goodsId) {
        return goodsType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setGoodsType(String goodsType, String goodsId) {
        this.goodsType =goodsType;
    }

}
