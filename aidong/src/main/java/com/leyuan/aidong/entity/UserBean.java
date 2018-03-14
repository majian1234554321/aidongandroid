package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FormatUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用户
 * Created by song on 2016/8/2.
 */
public class UserBean implements Parcelable, Serializable,  Comparable<UserBean> {
    //发现-人
    private String id;          //编号
    private String name;        //名字
    private String avatar;      //头像
    private String gender;      //性别
    private double distance;    //距离
    private boolean isFollow;   //是否关注
    public String signature;
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

    public boolean following;//判断关注我的人 我是否关注他,已废弃
    public boolean followed; //
    public int follows_count;

    public int rank;//
    public int score;//

    private StringBuffer tagString = new StringBuffer();

    protected UserBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatar = in.readString();
        gender = in.readString();
        distance = in.readDouble();
        isFollow = in.readByte() != 0;
        signature = in.readString();
        user_type = in.readString();
        publisher_id = in.readString();
        user_id = in.readString();
        personal_intro = in.readString();
        type = in.readString();
        followers_count = in.readInt();
        simple_intro = in.readString();
        image = in.readString();
        strength = in.readInt();
        tags = in.createStringArrayList();
        following = in.readByte() != 0;
        followed = in.readByte() != 0;
        follows_count = in.readInt();
        rank = in.readInt();
        score = in.readInt();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

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
        return id == null ? user_id : id;
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

        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeString(gender);
        dest.writeDouble(distance);
        dest.writeByte((byte) (isFollow ? 1 : 0));
        dest.writeString(signature);
        dest.writeString(user_type);
        dest.writeString(publisher_id);
        dest.writeString(user_id);
        dest.writeString(personal_intro);
        dest.writeString(type);
        dest.writeInt(followers_count);
        dest.writeString(simple_intro);
        dest.writeString(image);
        dest.writeInt(strength);
        dest.writeStringList(tags);
        dest.writeByte((byte) (following ? 1 : 0));
        dest.writeByte((byte) (followed ? 1 : 0));
        dest.writeInt(follows_count);
        dest.writeInt(rank);
        dest.writeInt(score);
    }


    public String getUserTypeByUserType() {
        return "Coach".equals(user_type) || "coach".equals(user_type) ? Constant.COACH : Constant.USER;
    }

    public String getTypeByType() {
        return "Coach".equals(type) || "coach".equals(type) ? Constant.COACH : Constant.USER;
    }

    @Override
    public int compareTo(UserBean o) {
        return this.rank - o.rank;
    }
}
