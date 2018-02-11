package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.utils.FormatUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用户
 * Created by song on 2016/8/2.
 */
public class UserBean implements Parcelable, Serializable {
    //发现-人
    private String id;          //编号
    private String name;        //名字
    private String avatar;      //头像
    private String gender;      //性别
    private double distance;    //距离
    private boolean isFollow;   //是否关注
    private String signature;
    private String user_type;

    //compat
    private String publisher_id;
    public String user_id;
    public String personal_intro;
    public String type;
    public int followers_count;

    public String simple_intro;
    public String image;

    public int strength;
    public ArrayList<String> tags;

    public boolean followed;
    public int follows_count;

    private StringBuffer tagString = new StringBuffer();

    public StringBuffer getTagString() {
        if (tagString == null) {
            tagString = new StringBuffer();
        }
        if (tagString.length() == 0 && tags != null) {
            for (int i = 0; i < tags.size(); i++) {
                if (i < tags.size() - 1) {
                    tagString.append(tags.get(i)).append(" | ");
                } else {
                    tagString.append(tags.get(i));
                }

            }
        }
        return tagString;
    }

    public UserBean(String id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getDistance() {
        return distance;
    }

    public String getDistanceFormat() {
        return FormatUtil.formatDistance(distance);
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public UserBean() {
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.gender);
        dest.writeDouble(this.distance);
        dest.writeByte(this.isFollow ? (byte) 1 : (byte) 0);
        dest.writeString(this.signature);
        dest.writeString(this.user_type);
    }

    protected UserBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readString();
        this.distance = in.readDouble();
        this.isFollow = in.readByte() != 0;
        this.signature = in.readString();
        this.user_type = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
