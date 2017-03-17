package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

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
    private CoordinateBean coordinate;

    public CoordinateBean getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(CoordinateBean coordinate) {
        this.coordinate = coordinate;
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
        dest.writeParcelable(this.coordinate, flags);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public VenuesBean() {
    }

    protected VenuesBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.brand_logo = in.readString();
        this.address = in.readString();
        this.distance = in.readDouble();
        this.price = in.readString();
        this.coordinate = in.readParcelable(CoordinateBean.class.getClassLoader());
        this.isChecked = in.readByte() != 0;
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
}
