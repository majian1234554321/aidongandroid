package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.data.DiscoverData;
import com.example.aidong .entity.data.DiscoverUserData;
import com.example.aidong .entity.data.DiscoverVenuesData;
import com.example.aidong .entity.data.DiscoverNewsData;

import rx.Subscriber;

/**
 * 发现
 * Created by song on 2016/8/29.
 */
public interface DiscoverModel {

    /**
     * 发现首页
     * @param subscriber Subscriber
     */
    void getDiscover(Subscriber<DiscoverData> subscriber);

    /**
     * 获取场馆列表
     * @param subscriber Subscriber
     * @param lat 纬度
     * @param lng 经度
     * @param page 页码
     */
    void getVenues(Subscriber<DiscoverVenuesData> subscriber, double lat, double lng, int page);

    /**
     * 获取用户列表
     * @param subscriber Subscriber
     * @param lat 纬度
     * @param lng 经度
     * @param page 页码
     * @param gender 性别
     * @param type 类型
     */
    void getUsers(Subscriber<DiscoverUserData> subscriber, double lat, double lng, int page, String gender, String type);

    /**
     * 获取新闻列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getNews(Subscriber<DiscoverNewsData> subscriber,int page);
}
