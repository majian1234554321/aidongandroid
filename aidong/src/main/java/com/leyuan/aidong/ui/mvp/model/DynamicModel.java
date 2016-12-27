package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.DynamicBean;

import java.util.List;

import rx.Subscriber;

/**
 * 爱动圈动态
 * Created by song on 2016/12/26.
 */
public interface DynamicModel {
    /**
     * 获取动态列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getDynamics(Subscriber<List<DynamicBean>> subscriber, int page);
}
