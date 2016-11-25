package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 购物车中商家实体
 * Created by song on 2016/9/23.
 */
public class ShopBean implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopname);
        dest.writeString(this.opentime);
        dest.writeTypedList(this.item);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    public ShopBean() {
    }

    protected ShopBean(Parcel in) {
        this.shopname = in.readString();
        this.opentime = in.readString();
        this.item = in.createTypedArrayList(GoodsBean.CREATOR);
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ShopBean> CREATOR = new Parcelable.Creator<ShopBean>() {
        @Override
        public ShopBean createFromParcel(Parcel source) {
            return new ShopBean(source);
        }

        @Override
        public ShopBean[] newArray(int size) {
            return new ShopBean[size];
        }
    };
}
