package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 规格
 * Created by song on 2016/11/9.
 */
public class LocalGoodsSkuBean {
    private String skuName;
    private List<GoodsSkuValueBean> skuValues;

    private boolean isSelected = false;
    private boolean isLastSelected = false;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public List<GoodsSkuValueBean> getSkuValues() {
        return skuValues;
    }

    public void setSkuValues(List<GoodsSkuValueBean> skuValues) {
        this.skuValues = skuValues;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isLastSelected() {
        return isLastSelected;
    }

    public void setLastSelected(boolean lastSelected) {
        isLastSelected = lastSelected;
    }

    @Override
    public String toString() {
        return "LocalGoodsSkuBean{" +
                "skuName='" + skuName + '\'' +
                ", skuValues=" + skuValues +
                '}';
    }
}
