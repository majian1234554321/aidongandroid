package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.utils.Constant;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class ProfileBean implements Parcelable {
    private String id;
    private String name;
    private String avatar;
    private String gender;
    private String birthday;
    private String age;
    private String zodiac;
    private String signture;
    private String province;
    private String city;
    private String area;
    private String height;
    private String weight;
    private String bmi;
    private String bust;
    private String waist;
    private String hip;
    private String movement_frequency;
    private String user_type;
    private String popularity;
    private String phone;
    public boolean followed;
    public int follows_count;
    public String mobile;
    public String intro;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getSignature() {
        return signture;
    }

    public void setSignature(String signture) {
        this.signture = signture;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHip() {
        return hip;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public String getFrequency() {
        return movement_frequency;
    }

    public void setFrequency(String frequency) {
        this.movement_frequency = frequency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public ProfileBean() {
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
        dest.writeString(this.birthday);
        dest.writeString(this.age);
        dest.writeString(this.zodiac);
        dest.writeString(this.signture);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.area);
        dest.writeString(this.height);
        dest.writeString(this.weight);
        dest.writeString(this.bmi);
        dest.writeString(this.bust);
        dest.writeString(this.waist);
        dest.writeString(this.hip);
        dest.writeString(this.movement_frequency);
        dest.writeString(this.user_type);
        dest.writeString(this.popularity);
        dest.writeString(this.phone);
        dest.writeByte(this.followed ? (byte) 1 : (byte) 0);
        dest.writeInt(this.follows_count);
        dest.writeString(this.mobile);

    }

    protected ProfileBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readString();
        this.birthday = in.readString();
        this.age = in.readString();
        this.zodiac = in.readString();
        this.signture = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.area = in.readString();
        this.height = in.readString();
        this.weight = in.readString();
        this.bmi = in.readString();
        this.bust = in.readString();
        this.waist = in.readString();
        this.hip = in.readString();
        this.movement_frequency = in.readString();
        this.user_type = in.readString();
        this.popularity = in.readString();
        this.phone = in.readString();
        this.followed = in.readByte() != 0;
        this.follows_count = in.readInt();
        this.mobile = in.readString();
    }

    public static final Creator<ProfileBean> CREATOR = new Creator<ProfileBean>() {
        @Override
        public ProfileBean createFromParcel(Parcel source) {
            return new ProfileBean(source);
        }

        @Override
        public ProfileBean[] newArray(int size) {
            return new ProfileBean[size];
        }
    };

    public String getUserTypeByUserType() {
        return "Coach".equals(user_type) || "coach".equals(user_type)? Constant.COACH:Constant.USER;
    }
}
