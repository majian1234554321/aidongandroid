package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.SearchHistoryBean;
import com.example.aidong .entity.data.CampaignData;
import com.example.aidong .entity.data.CourseData;
import com.example.aidong .entity.data.EquipmentData;
import com.example.aidong .entity.data.SearchNurtureData;
import com.example.aidong .entity.data.UserData;
import com.example.aidong .entity.data.VenuesData;

import java.util.List;

import rx.Subscriber;

/**
 * 搜索
 * Created by song on 2016/9/18.
 */
public interface SearchModel {

    void searchData(Subscriber<Object> subscriber,String keyword);

    /**
     * 搜索场馆
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchVenues(Subscriber<VenuesData> subscriber, String keyword, int page);

    /**
     * 搜索营养品
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchNurture(Subscriber<SearchNurtureData> subscriber, String keyword, int page);

    /**
     * 搜索课程
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchCourse(Subscriber<CourseData> subscriber, String keyword, int page);


    /**
     * 搜索装备
     * @param subscriber Subscriber
     * @param keyword 关键字
     * @param page 页码
     */
    void searchEquipment(Subscriber<EquipmentData> subscriber, String keyword, int page);

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


    void searchUserIt(Subscriber<UserData> subscriber, String keyword, int page);

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
