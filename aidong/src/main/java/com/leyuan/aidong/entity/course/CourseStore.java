package com.leyuan.aidong.entity.course;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by user on 2017/11/20.
 */
public class CourseStore implements Parcelable{

    String id;// 门店编号

    String name;// "门店",
    String address;// "地址",
    double[] coordinate;// [经度,维度],
    String classroom;// "教室"
    String seat;// "座位"
    String room;
    String tel;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    protected CourseStore(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        coordinate = in.createDoubleArray();
        classroom = in.readString();
        seat = in.readString();
        room = in.readString();
        tel  = in.readString();
    }

    public static final Creator<CourseStore> CREATOR = new Creator<CourseStore>() {
        @Override
        public CourseStore createFromParcel(Parcel in) {
            return new CourseStore(in);
        }

        @Override
        public CourseStore[] newArray(int size) {
            return new CourseStore[size];
        }
    };

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public  double[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate( double[] coordinate) {
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CourseStore{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", coordinate=" + Arrays.toString(coordinate) +
                ", classroom='" + classroom + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDoubleArray(coordinate);
        dest.writeString(classroom);
        dest.writeString(seat);
        dest.writeString(room);
        dest.writeString(tel);
    }
}
