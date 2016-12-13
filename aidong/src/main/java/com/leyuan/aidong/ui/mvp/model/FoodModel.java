package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.FoodAndVenuesBean;
import com.leyuan.aidong.entity.data.FoodDetailData;
import com.leyuan.aidong.entity.data.VenuesData;

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
    void getFoods(Subscriber<FoodAndVenuesBean> subscriber, int page);

    /**
     * 获取健康餐饮详情
     * @param subscriber Subscriber
     * @param id 餐饮id
     */
    void getFoodDetail(Subscriber<FoodDetailData> subscriber, String id);


    /**
     * 获取装备的自提场馆
     * @param subscriber Subscribers
     * @param skuCode sku码
     * @param page 页码
     */
    void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page);
}
