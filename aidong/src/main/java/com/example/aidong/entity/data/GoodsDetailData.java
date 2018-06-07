package com.example.aidong.entity.data;

import com.example.aidong .entity.GoodsDetailBean;

/**
 * Created by user on 2017/9/7.
 */
public class GoodsDetailData {

    private GoodsDetailBean product;

    public GoodsDetailBean getProduct() {
        return product;
    }

    public void setProduct(GoodsDetailBean product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "GoodsDetailBean{" +
                "nurture=" + product +
                '}';
    }
}
