package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.data.NurtureData;
import com.leyuan.support.entity.NurtureDetailBean;

import rx.Subscriber;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureModel {

    /**
     * 获取营养品列表
     * @param subscriber Subscribers
     * @param page 页码
     */
    void getNurtures(Subscriber<NurtureData> subscriber, int page);

    /**
     * 获取营养品详情
     * @param subscriber Subscribers
     * @param id id
     */
    void getNurtureDetail(Subscriber<NurtureDetailBean> subscriber, int id);
}
