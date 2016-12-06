package com.leyuan.aidong.entity.data;


import com.leyuan.aidong.entity.GoodsBean;

import java.util.List;

public class SearchGoodsData {

    private List<GoodsBean> product;

    public List<GoodsBean> getProduct() {
        return product;
    }

    public void setProduct(List<GoodsBean> product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "SearchGoodsData{" +
                "product=" + product +
                '}';
    }
}
