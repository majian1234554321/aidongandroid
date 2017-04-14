package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.utils.constant.DeliveryType;

import java.util.List;

/**
 * 购物车中商家实体
 * Created by song on 2016/9/23.
 */
public class ShopBean implements Parcelable {
    private List<GoodsBean> item;
    private @DeliveryType DeliveryBean pick_up;

    private boolean checked = false;            //标记该商店是否被选中

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

    public DeliveryBean getPickUp() {
        return pick_up;
    }

    public void setPickUp(DeliveryBean pick_up) {
        this.pick_up = pick_up;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.item);
        dest.writeParcelable(this.pick_up, flags);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    public ShopBean() {
    }

    protected ShopBean(Parcel in) {
        this.item = in.createTypedArrayList(GoodsBean.CREATOR);
        this.pick_up = in.readParcelable(DeliveryBean.class.getClassLoader());
        this.checked = in.readByte() != 0;
    }

    public static final Creator<ShopBean> CREATOR = new Creator<ShopBean>() {
        @Override
        public ShopBean createFromParcel(Parcel source) {
            return new ShopBean(source);
        }

        @Override
        public ShopBean[] newArray(int size) {
            return new ShopBean[size];
        }
    };

    public boolean allItemIsSoldOut() {
        if (item == null)
            return true;

        for (GoodsBean bean : item) {
            if (bean.isOnline() && bean.getStock() != 0) {
                return false;
            }
        }
        return true;
    }
}
