package com.leyuan.support.entity.data;

/**
 * 活动详情数据实体
 * Created by song on 2016/8/18.
 */
public class EquipmentDetailData {
    private EquipmentDetailData equipment;

    public EquipmentDetailData getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDetailData equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "EquipmentDetailData{" +
                "equipment=" + equipment +
                '}';
    }
}
