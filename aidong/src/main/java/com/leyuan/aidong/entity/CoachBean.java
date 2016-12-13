package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 教练
 * Created by song on 2016/8/2.
 */
public class CoachBean implements Parcelable {
    private String coach_id;
    private String name;
    private String avatar;
    private String gender;

    public String getCoachId() {
        return coach_id;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.coach_id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.gender);
    }

    public CoachBean() {
    }

    protected CoachBean(Parcel in) {
        this.coach_id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readString();
    }

    public static final Parcelable.Creator<CoachBean> CREATOR = new Parcelable.Creator<CoachBean>() {
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
