package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.SearchHistoryBean;
import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.FoodData;
import com.leyuan.aidong.entity.data.SearchGoodsData;
import com.leyuan.aidong.entity.data.UserData;
import com.leyuan.aidong.entity.data.VenuesData;

import java.util.List;

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
     * 搜索场馆
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchGoods(Subscriber<SearchGoodsData> subscriber, String keyword, int page);

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


    /**
     * 查找搜索历史
     */
    List<SearchHistoryBean> getSearchHistory();

    /**
     * 插入搜索历史
     * @param keyword 搜索历史
     */
    void insertSearchHistory(String keyword);

    /**
     * 删除搜索历史
     */
    void deleteSearchHistory();
}
