package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.NurtureDetailData;

import java.util.List;

import rx.Subscriber;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureModel {
    /**
     * 获取营养品分类信息
     * @return List<CategoryBean>
     */
    List<CategoryBean> getCategory();

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
    void getNurtureDetail(Subscriber<NurtureDetailData> subscriber, String id);
}
