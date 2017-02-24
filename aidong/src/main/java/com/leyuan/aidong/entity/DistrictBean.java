package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商圈左边筛选数据实体
 * Created by song on 2016/11/1.
 */
public class DistrictBean implements Parcelable ,Serializable{
    private String areaId;
    private String district_name;
    private List<DistrictDescBean> district_values;

    private boolean selected;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDistrictName() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public List<DistrictDescBean> getDistrictValues() {
        return district_values;
    }

    public void setDistrict_values(List<DistrictDescBean> district_values) {
        this.district_values = district_values;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.areaId);
        dest.writeString(this.district_name);
        dest.writeList(this.district_values);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    public DistrictBean() {
    }

    protected DistrictBean(Parcel in) {
        this.areaId = in.readString();
        this.district_name = in.readString();
        this.district_values = new ArrayList<DistrictDescBean>();
        in.readList(this.district_values, DistrictDescBean.class.getClassLoader());
        this.selected = in.readByte() != 0;
    }

    public static final Creator<DistrictBean> CREATOR = new Creator<DistrictBean>() {
        @Override
        public DistrictBean createFromParcel(Parcel source) {
            return new DistrictBean(source);
        }

        @Override
        public DistrictBean[] newArray(int size) {
            return new DistrictBean[size];
        }
    };

    @Override
    public String toString() {
        return "DistrictBean{" +
                "areaId='" + areaId + '\'' +
                ", district_name='" + district_name + '\'' +
                ", district_values=" + district_values +
                ", selected=" + selected +
                '}';
    }
}
