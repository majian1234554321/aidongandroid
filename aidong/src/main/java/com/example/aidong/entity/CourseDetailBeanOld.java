package com.example.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 课程详情实体
 * Created by song on 2016/8/13.
 */
@Deprecated
public class CourseDetailBeanOld implements Parcelable {
    private String code;        //课程编号
    private String name;        //课程名称
    private List<String> cover;       //封面
    private String class_date;       //上课日期
    private String class_time;       //上课时间
    private String break_time;       //下课时间
    private UserBean coach;   //课程
    private VenuesBean gym;    //场馆
    private String place;            //名额
    private String applied_count;    //已报名人数
    private List<UserBean> applied;  //报名的人
    private String introduce;        //课程介绍
    private String price;
    private String market_price;
    private String address;
    private String classroom;
    private String stock;
    private String status;    //# 0-预约已结束 1-已预约 2-预约人数已满 3-即将开始预约 4-“金额”待支付（已预约) 5-无需预约 6-可预约

    private String entry_start_time;
    private String orderId;

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getEntryStartTime() {
        return entry_start_time;
    }

    public void setEntry_start_time(String entry_start_time) {
        this.entry_start_time = entry_start_time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCover() {
        return cover;
    }

    public void setCover(List<String> cover) {
        this.cover = cover;
    }

    public String getClassDate() {
        return class_date;
    }

    public void setClass_date(String class_date) {
        this.class_date = class_date;
    }

    public String getClassTime() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getBreakTime() {
        return break_time;
    }

    public void setBreak_time(String break_time) {
        this.break_time = break_time;
    }

    public UserBean getCoach() {
        return coach;
    }

    public void setCoach(UserBean coach) {
        this.coach = coach;
    }

    public VenuesBean getGym() {
        return gym;
    }

    public void setGym(VenuesBean gym) {
        this.gym = gym;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAppliedCount() {
        return applied_count;
    }

    public void setApplied_count(String applied_count) {
        this.applied_count = applied_count;
    }

    public List<UserBean> getApplied() {
        return applied;
    }

    public void setApplied(List<UserBean> applied) {
        this.applied = applied;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseDetailBean{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", class_date='" + class_date + '\'' +
                ", class_time='" + class_time + '\'' +
                ", break_time='" + break_time + '\'' +
                ", coach=" + coach +
                ", gym=" + gym +
                ", place='" + place + '\'' +
                ", applied_count='" + applied_count + '\'' +
                ", applied=" + applied +
                ", introduce='" + introduce + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", classroom='" + classroom + '\'' +
                ", stock='" + stock + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


    public CourseDetailBeanOld() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeStringList(this.cover);
        dest.writeString(this.class_date);
        dest.writeString(this.class_time);
        dest.writeString(this.break_time);
        dest.writeParcelable(this.coach, flags);
        dest.writeParcelable(this.gym, flags);
        dest.writeString(this.place);
        dest.writeString(this.applied_count);
        dest.writeTypedList(this.applied);
        dest.writeString(this.introduce);
        dest.writeString(this.price);
        dest.writeString(this.market_price);
        dest.writeString(this.address);
        dest.writeString(this.classroom);
        dest.writeString(this.stock);
        dest.writeString(this.status);
        dest.writeString(this.entry_start_time);
        dest.writeString(this.orderId);
    }

    protected CourseDetailBeanOld(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.cover = in.createStringArrayList();
        this.class_date = in.readString();
        this.class_time = in.readString();
        this.break_time = in.readString();
        this.coach = in.readParcelable(UserBean.class.getClassLoader());
        this.gym = in.readParcelable(VenuesBean.class.getClassLoader());
        this.place = in.readString();
        this.applied_count = in.readString();
        this.applied = in.createTypedArrayList(UserBean.CREATOR);
        this.introduce = in.readString();
        this.price = in.readString();
        this.market_price = in.readString();
        this.address = in.readString();
        this.classroom = in.readString();
        this.stock = in.readString();
        this.status = in.readString();
        this.entry_start_time = in.readString();
        this.orderId = in.readString();
    }

    public static final Creator<CourseDetailBeanOld> CREATOR = new Creator<CourseDetailBeanOld>() {
        @Override
        public CourseDetailBeanOld createFromParcel(Parcel source) {
            return new CourseDetailBeanOld(source);
        }

        @Override
        public CourseDetailBeanOld[] newArray(int size) {
            return new CourseDetailBeanOld[size];
        }
    };
}
