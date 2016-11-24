package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 购物车中商家实体
 * Created by song on 2016/9/23.
 */
public class ShopBean {
    private String shopname;
    private String opentime;
    private List<GoodsBean> item;

    private boolean checked = false;            //标记该商店是否被选中


    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public List<GoodsBean> getItem() {
        return item;
    }

    public void setItem(List<GoodsBean> item) {
        this.item = item;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    @Override
    public String toString() {
        return "ShopBean{" +
                "shopname='" + shopname + '\'' +
                ", opentime='" + opentime + '\'' +
                ", item=" + item +
                ", checked=" + checked +
                '}';
    }
}
