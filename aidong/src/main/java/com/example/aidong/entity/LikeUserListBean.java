package com.example.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class LikeUserListBean implements Parcelable {
    public int counter;
    public List<UserBean> item ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.counter);
        dest.writeTypedList(this.item);
    }

    public LikeUserListBean() {
    }

    protected LikeUserListBean(Parcel in) {
        this.counter = in.readInt();
        this.item = in.createTypedArrayList(UserBean.CREATOR);
    }

    public static final Parcelable.Creator<LikeUserListBean> CREATOR = new Parcelable.Creator<LikeUserListBean>() {
        @Override
        public LikeUserListBean createFromParcel(Parcel source) {
            return new LikeUserListBean(source);
        }

        @Override
        public LikeUserListBean[] newArray(int size) {
            return new LikeUserListBean[size];
        }
    };
}
