package com.leyuan.support.mvp.model;


import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.entity.VenuesDetailBean;

import java.util.List;

import rx.Subscriber;

/**
 * 场馆
 * Created by Song on 2016/8/2.
 */
public interface VenuesModel {

    /**
     * 获取场馆列表
     * @param subscriber 返回Subscriber
     * @param page 页码
     */
     void getVenues(Subscriber<List<VenuesBean>> subscriber, int page);

    /**
     * 获取场馆详情
     * @param subscribe 返回Subscriber
     * @param id 场馆id
     */
     void getVenuesDetail(Subscriber<VenuesDetailBean> subscribe, int id);
}
