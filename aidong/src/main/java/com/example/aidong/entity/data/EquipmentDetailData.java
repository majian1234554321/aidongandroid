package com.example.aidong.entity.data;

import com.example.aidong.entity.EquipmentDetailBean;

/**
 * 活动详情数据实体
 * Created by song on 2016/8/18.
 */
public class EquipmentDetailData {
    private EquipmentDetailBean equipment;


    public EquipmentDetailBean getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDetailBean equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "EquipmentDetailData{" +
                "equipment=" + equipment +
                '}';
    }
}
