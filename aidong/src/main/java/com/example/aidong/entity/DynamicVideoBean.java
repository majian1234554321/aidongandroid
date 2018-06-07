package com.example.aidong.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class DynamicVideoBean implements Parcelable {
    public String url;
    public String cover;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.cover);
    }

    public DynamicVideoBean() {
    }

    protected DynamicVideoBean(Parcel in) {
        this.url = in.readString();
        this.cover = in.readString();
    }

    public static final Parcelable.Creator<DynamicVideoBean> CREATOR = new Parcelable.Creator<DynamicVideoBean>() {
        @Override
        public DynamicVideoBean createFromParcel(Parcel source) {
            return new DynamicVideoBean(source);
        }

        @Override
        public DynamicVideoBean[] newArray(int size) {
            return new DynamicVideoBean[size];
        }
    };
}
