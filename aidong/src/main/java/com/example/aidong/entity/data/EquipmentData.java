package com.example.aidong.entity.data;

import com.example.aidong .entity.EquipmentBean;

import java.util.ArrayList;

/**
 * 装备列表数据实体
 * Created by song on 2016/8/18.
 */
public class EquipmentData {
    private ArrayList<EquipmentBean> equipment;

    public ArrayList<EquipmentBean> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<EquipmentBean> equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "EquipmentData{" +
                "equipment=" + equipment +
                '}';
    }
}
