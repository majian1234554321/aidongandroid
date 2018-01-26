package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 商品Sku实体
 * Created by song on 2016/11/16.
 */
public class GoodsSkuBean {

    public String code;               //商品规格
    public List<String> value;        //['规格-1-值','规格-2-值'...]
    public String cover;              //图片封面
    public String market_price;       //市场价
    private int stock;                //价格
    public String price;              //商品详情
    public VenuesBean gym;
    public int limit_amount;//限购数量




    @Override
    public String toString() {
        return "Item{" +
                "id='" + code + '\'' +
                ", value=" + value +
                ", cover='" + cover + '\'' +
                ", market_price='" + market_price + '\'' +
                ", stock='" + stock + '\'' +
                ", price='" + price + '\'' +
                ", gym=" + gym +
                '}';
    }

    /**
     * @return 大于等于0返回自己,小于0表示不限库存,返回999
     */
    public int getStock() {
        return stock > -1 ? stock : 999;
    }
}
