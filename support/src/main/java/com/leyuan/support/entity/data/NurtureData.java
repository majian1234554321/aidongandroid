package com.leyuan.support.entity.data;

import com.leyuan.support.entity.NurtureBean;

import java.util.ArrayList;

/**
 * 营养品数据实体
 * Created by song on 2016/8/15.
 */
public class NurtureData {
    private ArrayList<NurtureBean> nutrition;

    public ArrayList<NurtureBean> getNutrition() {
        return nutrition;
    }

    public void setNutrition(ArrayList<NurtureBean> nutrition) {
        this.nutrition = nutrition;
    }

    @Override
    public String toString() {
        return "NurtureData{" +
                "nutrition=" + nutrition +
                '}';
    }
}
