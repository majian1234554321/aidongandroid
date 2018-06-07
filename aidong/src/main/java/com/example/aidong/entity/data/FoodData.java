package com.example.aidong.entity.data;

import com.example.aidong .entity.FoodBean;

import java.util.List;

/**
 * 健康餐饮
 * Created by song on 2016/9/18.
 */
public class FoodData {
    private List<FoodBean> food;

    public List<FoodBean> getFood() {
        return food;
    }

    public void setFood(List<FoodBean> food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "FoodData{" +
                "food=" + food +
                '}';
    }
}
