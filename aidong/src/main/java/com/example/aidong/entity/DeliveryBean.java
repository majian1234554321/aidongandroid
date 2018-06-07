package com.example.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.aidong .utils.constant.DeliveryType;

/**
 * 商品详情配送实体
 * Created by song on 2016/9/22.
 */
public class DeliveryBean implements Parcelable {
    private String type;        //type: 取货方式, #0-快递　1-自提
    private VenuesBean info;
    private boolean is_send;     //是否支持快递

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

    public boolean isSend() {
        return is_send;
    }

    public void setIs_send(boolean is_send) {
        this.is_send = is_send;
    }

    public DeliveryBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeParcelable(this.info, flags);
        dest.writeByte(this.is_send ? (byte) 1 : (byte) 0);
    }

    protected DeliveryBean(Parcel in) {
        this.type = in.readString();
        this.info = in.readParcelable(VenuesBean.class.getClassLoader());
        this.is_send = in.readByte() != 0;
    }

    public static final Creator<DeliveryBean> CREATOR = new Creator<DeliveryBean>() {
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
