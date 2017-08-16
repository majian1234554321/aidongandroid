package com.leyuan.aidong.entity;

import android.text.TextUtils;

import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FormatUtil;

import java.util.List;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;

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
        if (TextUtils.equals(goodsType, Constant.GOODS_NUTRITION) && FormatUtil.parseInt(goodsId) > 9999) {
            return Constant.GOODS_FOODS;
        }
        return goodsType;
    }

    public String getGoodsType() {
        if (TextUtils.equals(goodsType, Constant.GOODS_NUTRITION) && FormatUtil.parseInt(id) > 9999) {
            return Constant.GOODS_FOODS;
        }
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setGoodsType(String goodsType, String goodsId) {
        this.goodsType = getRealGoodsType(goodsType, goodsId);
    }

    public void setDeliveryBeanByGoodsType(String goodsType) {
        if (TextUtils.equals(goodsType, Constant.GOODS_FOODS)){
            pick_up = new DeliveryBean();
            pick_up.setInfo(null);
            pick_up.setType(DELIVERY_EXPRESS);
            pick_up.setIs_send(true);
        }
    }
}
