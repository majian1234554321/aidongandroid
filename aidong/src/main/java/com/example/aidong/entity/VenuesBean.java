package com.example.aidong.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.aidong .utils.FormatUtil;

/**
 * 场馆实体
 * Created by pc on 2016/8/2.
 */
public class VenuesBean implements Parcelable {
    private String id;
    private String name;            //场馆名字
    private String brand_logo;      //场馆封面
    private String address;         //场馆地址
    private double distance;        //距离
    private String price;           //价格

//    private CoordinateBean coordinate;

    private String admission;
    private String brand_name;
    public String lat;
    public String lng;
    public String link_id;
    public String cover;

    public String getAdmission() {
        return admission;
    }

    public void setAdmission(String admission) {
        this.admission = admission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getGymId() {
        return id;
    }

    public void setGymId(String gym_id) {
        this.id = gym_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandLogo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    @Override
    public String toString() {
        return "VenuesBean{" +
                "gym_id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand_logo='" + brand_logo + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public VenuesBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.brand_logo);
        dest.writeString(this.address);
        dest.writeDouble(this.distance);
        dest.writeString(this.price);
        dest.writeString(this.admission);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeString(brand_name);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.link_id);
        dest.writeString(this.cover);
    }

    protected VenuesBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.brand_logo = in.readString();
        this.address = in.readString();
        this.distance = in.readDouble();
        this.price = in.readString();
        this.admission = in.readString();
        this.isChecked = in.readByte() != 0;
        this.brand_name = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.link_id = in.readString();
        this.cover = in.readString();
    }

    public static final Creator<VenuesBean> CREATOR = new Creator<VenuesBean>() {
        @Override
        public VenuesBean createFromParcel(Parcel source) {
            return new VenuesBean(source);
        }

        @Override
        public VenuesBean[] newArray(int size) {
            return new VenuesBean[size];
        }
    };

    @SuppressLint("DefaultLocale")
    public String getDistanceFormat() {
        return FormatUtil.formatDistanceStore(distance);
    }
}
