package com.example.aidong.entity;

import java.util.ArrayList;

/**
 * 健康餐饮界面实体
 * Created by song on 2016/8/22.
 */
public class FoodAndVenuesBean {
    private ArrayList<FoodBean> food;
    private ArrayList<VenuesBean> pick_up_gym;

    public ArrayList<FoodBean> getFood() {
        return food;
    }

    public void setFood(ArrayList<FoodBean> food) {
        this.food = food;
    }

    public ArrayList<VenuesBean> getPick_up_gym() {
        return pick_up_gym;
    }

    public void setPick_up_gym(ArrayList<VenuesBean> pick_up_gym) {
        this.pick_up_gym = pick_up_gym;
    }

    @Override
    public String toString() {
        return "FoodAndVenuesBean{" +
                "food=" + food +
                ", pick_up_gym=" + pick_up_gym +
                '}';
    }
}
