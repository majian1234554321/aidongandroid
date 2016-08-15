package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.FoodBean;
import com.leyuan.support.entity.FoodDetailBean;

import java.util.List;

import rx.Subscriber;

/**
 * 健康餐饮
 * Created by song on 2016/8/15.
 */
public interface FoodModel {
    /**
     * 获取健康餐饮列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getFoods(Subscriber<List<FoodBean>> subscriber, int page);

    /**
     * 获取健康餐饮详情
     * @param subscriber Subscriber
     * @param id 餐饮id
     */
    void getFoodDetail(Subscriber<FoodDetailBean> subscriber, int id);

    //void createFoodOrder();
}
