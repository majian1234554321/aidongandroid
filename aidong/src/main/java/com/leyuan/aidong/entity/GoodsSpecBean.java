package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 商品规格实体
 * Created by song on 2016/11/16.
 */
public class GoodsSpecBean {
    public List<String> name;       //规格名
    public List<GoodsSkuBean> item;       //规格值
    public boolean isSelected = false;  //该规格是否确定
    @Override
    public String toString() {
        return "Spec{" +
                "name=" + name +
                ", item=" + item +
                '}';
    }
}
