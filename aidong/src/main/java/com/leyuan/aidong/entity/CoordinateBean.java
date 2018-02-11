package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 坐标实体
 * Created by song on 2017/3/13.
 */
public class CoordinateBean implements Parcelable {
    public String lat;
    public String lng;
    public String position_name;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.position_name);
    }

    public CoordinateBean() {
    }

    public CoordinateBean(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected CoordinateBean(Parcel in) {
        this.lat = in.readString();
        this.lng = in.readString();
        this.position_name = in.readString();
    }

    public static final Parcelable.Creator<CoordinateBean> CREATOR = new Parcelable.Creator<CoordinateBean>() {
        @Override
        public CoordinateBean createFromParcel(Parcel source) {
            return new CoordinateBean(source);
        }

        @Override
        public CoordinateBean[] newArray(int size) {
            return new CoordinateBean[size];
        }
    };
}
