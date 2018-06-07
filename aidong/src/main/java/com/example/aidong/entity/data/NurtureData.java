package com.example.aidong.entity.data;

import com.example.aidong .entity.NurtureBean;

import java.util.ArrayList;

/**
 * 营养品数据实体
 * Created by song on 2016/8/15.
 */
public class NurtureData {
    private ArrayList<NurtureBean> nutrition;
    private ArrayList<NurtureBean> foods;

    public ArrayList<NurtureBean> getNutrition() {
        return nutrition;
    }

    public void setNutrition(ArrayList<NurtureBean> nutrition) {
        this.nutrition = nutrition;
    }

    public ArrayList<NurtureBean> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<NurtureBean> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "NurtureData{" +
                "nutrition=" + nutrition +
                '}';
    }
}
