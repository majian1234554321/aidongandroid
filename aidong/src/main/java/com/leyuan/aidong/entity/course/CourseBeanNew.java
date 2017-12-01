package com.leyuan.aidong.entity.course;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.entity.CoachBean;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseBeanNew implements Parcelable {
    public static final int NORMAL = 0;
    public static final int APPOINTED = 1;
    public static final int APPOINTED_NO_PAY = 2;
    public static final int QUEUED = 3;
    public static final int FEW = 4;
    public static final int QUEUEABLE = 5;
    public static final int FULL = 6;
    public static final int END = 7;

    String id;// 课程编号
    String name;// 课程名
    String class_time;//  上课时间 - 下课时间
    ArrayList<String> tags;//  ["标签"]
    String strength;// 强度
    CoachBean coach;

    boolean reservable = true;// 　是否可以预约#0-否　１-是
    boolean member_only;
    boolean member;

    String reserve_time;// 预约时间

    double price;//价格
    double member_price;// 会员价格
    String class_start_time;  //上课时间
    String class_end_time; // 下课时间
    String cover;// 封面
    ArrayList<String> image;
    CourseStore store;//门店
    String introduce;//课程详情
    CourseSeat seat;
    String class_room;
    String class_store;

    int status; //0-正常 1-已预约 2-已预约，未付款 3-已排队 4-紧张 5-可排队 6-已满员 7-已结束
    String seatChoosed;


    private StringBuffer tagString = new StringBuffer();

    public StringBuffer getTagString() {
        if (tagString == null) {
            tagString = new StringBuffer();
        }
        if (tagString.length() == 0) {
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

    protected CourseBeanNew(Parcel in) {
        id = in.readString();
        name = in.readString();
        class_time = in.readString();
        tags = in.createStringArrayList();
        strength = in.readString();
        coach = in.readParcelable(CoachBean.class.getClassLoader());
        reservable = in.readByte() != 0;
        member_only =  in.readByte() != 0;
        member = in.readByte() != 0;

        reserve_time = in.readString();
        price = in.readDouble();
        member_price = in.readDouble();
        class_start_time = in.readString();
        class_end_time = in.readString();
        cover = in.readString();
        image = in.createStringArrayList();
        store = in.readParcelable(CourseStore.class.getClassLoader());
        introduce = in.readString();
        seat = in.readParcelable(CourseSeat.class.getClassLoader());
        class_room = in.readString();
        class_store = in.readString();
        status = in.readInt();
        seatChoosed = in.readString();
    }

    public static final Creator<CourseBeanNew> CREATOR = new Creator<CourseBeanNew>() {
        @Override
        public CourseBeanNew createFromParcel(Parcel in) {
            return new CourseBeanNew(in);
        }

        @Override
        public CourseBeanNew[] newArray(int size) {
            return new CourseBeanNew[size];
        }
    };


    public String getSeatChoosed() {
        return seatChoosed;
    }

    public void setSeatChoosed(String seatChoosed) {
        this.seatChoosed = seatChoosed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public ArrayList<String> getTag() {
        return tags;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getClass_end_time() {
        return class_end_time;
    }

    public void setClass_end_time(String class_end_time) {
        this.class_end_time = class_end_time;
    }

    public String getClass_start_time() {
        return class_start_time;
    }

    public void setClass_start_time(String class_start_time) {
        this.class_start_time = class_start_time;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public void setTag(ArrayList<String> tag) {
        this.tags = tag;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }


    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public int getReservable() {
        return reservable?1:0;
    }

    public void setReservable(int reservable) {
        this.reservable = reservable == 1;
    }

    public boolean isMember_only() {
        return member_only;
    }

    public void setMember_only(boolean member_only) {
        this.member_only = member_only;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public CoachBean getCoach() {
        return coach;
    }

    public void setCoach(CoachBean coach) {
        this.coach = coach;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CourseStore getStore() {
        return store;
    }

    public void setStore(CourseStore store) {
        this.store = store;
    }

    public double getMember_price() {
        return member_price;
    }

    public void setMember_price(double member_price) {
        this.member_price = member_price;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public CourseSeat getSeat() {
        return seat;
    }

    public void setSeat(CourseSeat seat) {
        this.seat = seat;
    }

    public static int getNORMAL() {
        return NORMAL;
    }

    public String getClass_room() {
        return class_room;
    }

    public void setClass_room(String class_room) {
        this.class_room = class_room;
    }

    public String getClass_store() {
        return class_store;
    }

    public void setClass_store(String class_store) {
        this.class_store = class_store;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(class_time);
        dest.writeStringList(tags);
        dest.writeString(strength);
        dest.writeParcelable(coach, flags);
        dest.writeByte((byte) (reservable ? 1 : 0));
        dest.writeByte((byte) (member_only ? 1 : 0));
        dest.writeByte((byte) (member ? 1 : 0));
        dest.writeString(reserve_time);
        dest.writeDouble(price);
        dest.writeDouble(member_price);
        dest.writeString(class_start_time);
        dest.writeString(class_end_time);
        dest.writeString(cover);
        dest.writeStringList(image);
        dest.writeParcelable(store, flags);
        dest.writeString(introduce);
        dest.writeParcelable(seat, flags);
        dest.writeString(class_room);
        dest.writeString(class_store);
        dest.writeInt(status);
        dest.writeString(seatChoosed);
    }
}
