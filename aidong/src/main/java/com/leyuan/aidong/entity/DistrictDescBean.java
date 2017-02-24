package com.leyuan.aidong.entity;

import java.io.Serializable;

/**
 * 商圈筛选数据右边实体
 * Created by song on 2016/11/1.
 */
public class DistrictDescBean implements Serializable{

    private String areaId;
    private String area;
    private boolean selected;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "DistrictDescBean{" +
                "areaId='" + areaId + '\'' +
                ", area='" + area + '\'' +
                ", selected=" + selected +
                '}';
    }
}
