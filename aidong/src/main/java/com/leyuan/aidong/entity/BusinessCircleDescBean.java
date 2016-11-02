package com.leyuan.aidong.entity;

/**
 * 商圈筛选数据右边实体
 * Created by song on 2016/11/1.
 */
public class BusinessCircleDescBean {

    private String areaId;
    private String areaName;
    private boolean isSelected;

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
