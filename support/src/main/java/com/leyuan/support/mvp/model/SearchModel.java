package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.data.CampaignData;
import com.leyuan.support.entity.data.CourseData;
import com.leyuan.support.entity.data.FoodData;
import com.leyuan.support.entity.data.UserData;
import com.leyuan.support.entity.data.VenuesData;

import rx.Subscriber;

/**
 * 搜索
 * Created by song on 2016/9/18.
 */
public interface SearchModel {

    /**
     * 搜索场馆
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchVenues(Subscriber<VenuesData> subscriber, String keyword, int page);

    /**
     * 搜索教练
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchCourse(Subscriber<CourseData> subscriber, String keyword, int page);


    /**
     * 搜索健康餐饮
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchFood(Subscriber<FoodData> subscriber, String keyword, int page);

    /**
     * 搜索活动
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchCampaign(Subscriber<CampaignData> subscriber, String keyword, int page);

    /**
     * 搜索用户
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchUser(Subscriber<UserData> subscriber, String keyword, int page);


}