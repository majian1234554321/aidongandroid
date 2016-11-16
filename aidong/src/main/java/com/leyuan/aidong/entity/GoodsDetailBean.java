package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 营养品，健康餐饮，装备商品详情
 * Created by song on 2016/9/22.
 */
public class GoodsDetailBean {
    public String food_id;    //商品编号
    public String name;       //商品名称
    public List<String> image;       //图片地址
    public String price;       //商品价格
    public String market_price;       //商品市场价
    public String introduce;       //商品详情
    public Spec spec;
    public List<CouponBean> coupon;

    public class Spec {
        public List<String> name;       //规格名
        public List<Item> item;       //规格值

        public class Item {
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

        @Override
        public String toString() {
            return "Spec{" +
                    "name=" + name +
                    ", item=" + item +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsDetailBean{" +
                "food_id='" + food_id + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", introduce='" + introduce + '\'' +
                ", spec=" + spec +
                ", coupon=" + coupon +
                '}';
    }
}
