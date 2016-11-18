package com.leyuan.aidong.entity;

/**
 * sku值
 * Created by song on 2016/11/16.
 */
public class GoodsSkuValueBean {
    private String value;
    private boolean isSelected = false;
    private boolean isAvailable = true;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
