package com.example.aidong.entity.data;

import com.example.aidong .entity.GoodsBean;

import java.util.List;

/**
 * 首页小分类详情实体
 * Created by song on 2016/11/11.
 */
public class BrandData {
    private List<GoodsBean> item;

    public List<GoodsBean> getItem() {
        return item;
    }

    public void setItem(List<GoodsBean> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "BrandData{" +
                "item=" + item +
                '}';
    }
}
