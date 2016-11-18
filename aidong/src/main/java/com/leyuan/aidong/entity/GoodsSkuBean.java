package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 商品Sku实体
 * Created by song on 2016/11/16.
 */
public class GoodsSkuBean {

    public String item_id;       //商品规格
    public List<String> value;       //['规格-1-值','规格-2-值'...]
    public String cover;       //图片封面
    public String market_price;       //市场价
    public String stock;       //价格
    public String price;       //商品详情
    public VenuesBean gym;

    @Override
    public String toString() {
        return "Item{" +
                "item_id='" + item_id + '\'' +
                ", value=" + value +
                ", cover='" + cover + '\'' +
                ", market_price='" + market_price + '\'' +
                ", stock='" + stock + '\'' +
                ", price='" + price + '\'' +
                ", gym=" + gym +
                '}';
    }

}
