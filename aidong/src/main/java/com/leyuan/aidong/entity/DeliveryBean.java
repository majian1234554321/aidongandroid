package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.utils.constant.DeliveryType;

/**
 * 商品详情配送实体
 * Created by song on 2016/9/22.
 */
public class DeliveryBean implements Parcelable {
    public String type;        //type: 取货方式, #0-快递　1-自提
    public VenuesBean info;

    @DeliveryType
    public String getType() {
        return type;
    }

    public void setType(@DeliveryType String type) {
        this.type = type;
    }

    public VenuesBean getInfo() {
        return info;
    }

    public void setInfo(VenuesBean info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeParcelable(this.info, flags);
    }

    public DeliveryBean() {
    }

    protected DeliveryBean(Parcel in) {
        this.type = in.readString();
        this.info = in.readParcelable(VenuesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DeliveryBean> CREATOR = new Parcelable.Creator<DeliveryBean>() {
        @Override
        public DeliveryBean createFromParcel(Parcel source) {
            return new DeliveryBean(source);
        }

        @Override
        public DeliveryBean[] newArray(int size) {
            return new DeliveryBean[size];
        }
    };
}
