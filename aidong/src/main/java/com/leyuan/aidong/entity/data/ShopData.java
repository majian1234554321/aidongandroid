package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.ShopBean;

import java.util.List;

/**
 * 购物车
 * Created by song on 2016/12/29.
 */
public class ShopData {
    private List<ShopBean> cart;

    public List<ShopBean> getCart() {
        return cart;
    }

    public void setCart(List<ShopBean> cart) {
        this.cart = cart;
    }
}
