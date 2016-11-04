package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 收货地址实体
 * Created by song on 2016/9/21.
 */
public class AddressBean implements Parcelable{
    private String address_id;
    private String name;
    private String mobile;
    private String address;

    public String getId() {
        return address_id;
    }

    public void setId(String id) {
        this.address_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address_id);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.address);
    }

    protected AddressBean(Parcel in) {
        this.address_id = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.address = in.readString();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };

    @Override
    public String toString() {
        return "AddressBean{" +
                "id='" + address_id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
