package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.BrandBean;
import com.leyuan.support.entity.HomeBean;

import java.util.List;

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
    void getRecommendList(Subscriber<List<HomeBean>> subscriber, int page);

    /**
     * 获取首页分类详情
     * @param subscriber Subscriber
     * @param id 小分类id
     * @param page 页码
     */
    void getBrandDetail(Subscriber<BrandBean> subscriber, int id, int page);


}
