package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BrandBean;
import com.leyuan.aidong.entity.data.BannerData;
import com.leyuan.aidong.entity.data.HomeData;

import rx.Subscriber;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public interface HomeModel {
    /**
     * 获取首页推荐列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getRecommendList(Subscriber<HomeData> subscriber, int page);

    /**
     * 获取首页分类详情
     * @param subscriber Subscriber
     * @param id 小分类id
     * @param page 页码
     */
    void getBrandDetail(Subscriber<BrandBean> subscriber, int id, int page);

    /**
     * 获取Banner
     * @param subscriber Subscriber
     */
    void getBanners(Subscriber<BannerData> subscriber);


}