package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class ProfileBean implements Parcelable {
    private String name;
    private String avatar;
    private String gender;
    private String birthday;
    private String age;
    private String zodiac;
    private List<String> photowall;
    private String signature;
    private String province;
    private String city;
    private String area;
    private String height;
    private String weight;
    private String bmi;
    private String bust;
    private String waist;
    private String hip;
    private String frequency;

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

    public List<String> getPhotowall() {
        return photowall;
    }

    public void setPhotowall(List<String> photowall) {
        this.photowall = photowall;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.gender);
        dest.writeString(this.birthday);
        dest.writeString(this.age);
        dest.writeString(this.zodiac);
        dest.writeStringList(this.photowall);
        dest.writeString(this.signature);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.area);
        dest.writeString(this.height);
        dest.writeString(this.weight);
        dest.writeString(this.bmi);
        dest.writeString(this.bust);
        dest.writeString(this.waist);
        dest.writeString(this.hip);
        dest.writeString(this.frequency);
    }

    public ProfileBean() {
    }

    protected ProfileBean(Parcel in) {
        this.name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readString();
        this.birthday = in.readString();
        this.age = in.readString();
        this.zodiac = in.readString();
        this.photowall = in.createStringArrayList();
        this.signature = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.area = in.readString();
        this.height = in.readString();
        this.weight = in.readString();
        this.bmi = in.readString();
        this.bust = in.readString();
        this.waist = in.readString();
        this.hip = in.readString();
        this.frequency = in.readString();
    }

    public static final Parcelable.Creator<ProfileBean> CREATOR = new Parcelable.Creator<ProfileBean>() {
        @Override
        public ProfileBean createFromParcel(Parcel source) {
            return new ProfileBean(source);
        }

        @Override
        public ProfileBean[] newArray(int size) {
            return new ProfileBean[size];
        }
    };
}
