package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 商圈左边筛选数据实体
 * Created by song on 2016/11/1.
 */
public class BusinessCircleBean {
    private String areaId;
    private String areaName;
    private List<BusinessCircleDescBean> district;

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

    public List<BusinessCircleDescBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<BusinessCircleDescBean> district) {
        this.district = district;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
