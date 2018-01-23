package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 教练
 * Created by song on 2016/8/2.
 */
public class CoachBean implements Parcelable {
    private String idong;
    private String name;
    private String avatar;
    private String gender;
    private String mobile;
    private String id;
    private String introduce;//'教练介绍'
    private String contact;
    private String cover;
    private String follow_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoachId() {
        return idong;
    }

    public void setCoach_id(String coach_id) {
        this.idong = coach_id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCover() {
        return cover;
    }

    public String getFollow_count() {
        return follow_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idong);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.gender);
        dest.writeString(this.mobile);
        dest.writeString(this.id);
        dest.writeString(this.introduce);
        dest.writeString(this.contact);
        dest.writeString(this.cover);
        dest.writeString(this.follow_count);
    }

    public CoachBean() {
    }

    protected CoachBean(Parcel in) {
        this.idong = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readString();
        this.mobile = in.readString();
        this.id = in.readString();
        this.introduce = in.readString();
        this.contact = in.readString();
        this.cover = in.readString();
        this.follow_count = in.readString();
    }

    public static final Creator<CoachBean> CREATOR = new Creator<CoachBean>() {
        @Override
        public CoachBean createFromParcel(Parcel source) {
            return new CoachBean(source);
        }

        @Override
        public CoachBean[] newArray(int size) {
            return new CoachBean[size];
        }
    };
}
