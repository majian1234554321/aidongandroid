package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 商圈左边筛选数据实体
 * Created by song on 2016/11/1.
 */
public class BusinessCircleBean implements Parcelable {
    private String areaId;
    private String areaName;
    private List<BusinessCircleDescBean> district;

    private BusinessCircleDescBean selectedRightCircleBean;
    private boolean selected;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<BusinessCircleDescBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<BusinessCircleDescBean> district) {
        this.district = district;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public BusinessCircleDescBean getSelectedRightCircleBean() {
        return selectedRightCircleBean;
    }

    public void setSelectedRightCircleBean(BusinessCircleDescBean selectedRightCircleBean) {
        this.selectedRightCircleBean = selectedRightCircleBean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.areaId);
        dest.writeString(this.areaName);
        dest.writeList(this.district);
    }

    public BusinessCircleBean() {
    }

    protected BusinessCircleBean(Parcel in) {
        this.areaId = in.readString();
        this.areaName = in.readString();
        this.district = new ArrayList<BusinessCircleDescBean>();
        in.readList(this.district, BusinessCircleDescBean.class.getClassLoader());
    }

    public static final Creator<BusinessCircleBean> CREATOR = new Creator<BusinessCircleBean>() {
        @Override
        public BusinessCircleBean createFromParcel(Parcel source) {
            return new BusinessCircleBean(source);
        }

        @Override
        public BusinessCircleBean[] newArray(int size) {
            return new BusinessCircleBean[size];
        }
    };
}
