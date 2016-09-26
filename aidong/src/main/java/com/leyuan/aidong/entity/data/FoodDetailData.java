package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.FoodDetailBean;

/**
 * 健康餐饮
 * Created by song on 2016/9/22.
 */
public class FoodDetailData {
    private FoodDetailBean food;

    public FoodDetailBean getFood() {
        return food;
    }

    public void setFood(FoodDetailBean food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "FoodDetailData{" +
                "food=" + food +
                '}';
    }
}
