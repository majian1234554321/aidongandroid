package com.example.aidong.entity;

/**
 * 更改配送信息
 * Created by song on 2017/3/8.
 */
public class UpdateDeliveryBean {

    private GoodsBean goods;

    private DeliveryBean deliveryInfo;

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public DeliveryBean getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryBean deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
