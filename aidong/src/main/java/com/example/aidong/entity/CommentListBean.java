package com.example.aidong.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CommentListBean implements Parcelable {
    public int count;
    public List<CommentBean> item ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(this.item);
    }

    public CommentListBean() {
    }

    protected CommentListBean(Parcel in) {
        this.count = in.readInt();
        this.item = in.createTypedArrayList(CommentBean.CREATOR);
    }

    public static final Parcelable.Creator<CommentListBean> CREATOR = new Parcelable.Creator<CommentListBean>() {
        @Override
        public CommentListBean createFromParcel(Parcel source) {
            return new CommentListBean(source);
        }

        @Override
        public CommentListBean[] newArray(int size) {
            return new CommentListBean[size];
        }
    };
}
