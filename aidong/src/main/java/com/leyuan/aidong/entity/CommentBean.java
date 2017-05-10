package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 爱动圈评论
 * Created by song on 2017/1/14.
 */
public class CommentBean implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("content")
    private String content;
    @SerializedName("published_at")
    private String published_at;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("publisher")
    private UserBean publisher;


    public UserBean getPublisher() {
        return publisher;
    }

    public void setPublisher(UserBean publisher) {
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getPublishedAt() {
        return published_at;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishedAt(String published_at) {
        this.published_at = published_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.published_at);
        dest.writeString(this.created_at);
        dest.writeParcelable(this.publisher, flags);
    }

    public CommentBean() {
    }

    protected CommentBean(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.published_at = in.readString();
        this.created_at = in.readString();
        this.publisher = in.readParcelable(UserBean.class.getClassLoader());
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel source) {
            return new CommentBean(source);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };
}
