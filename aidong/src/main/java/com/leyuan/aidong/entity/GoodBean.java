package com.leyuan.aidong.entity;


/**
 * 首页商品实体
 * Created by song on 2016/7/14.
 */
public class GoodBean {

    private String ruleId;           //商品编码
    private String foodName;         //商品封面
    private String foodUrl;          //商品名字
    private String foodPrice;         //商品售价
    private String goodsType;         //商品类型 营养餐 装备还是其他

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodUrl() {
        return foodUrl;
    }

    public void setFoodUrl(String foodUrl) {
        this.foodUrl = foodUrl;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
