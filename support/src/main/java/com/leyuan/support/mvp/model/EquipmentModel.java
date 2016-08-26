package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.data.EquipmentData;
import com.leyuan.support.entity.data.EquipmentDetailData;

import rx.Subscriber;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public interface EquipmentModel {

    /**
     * 获取装备列表
     * @param subscriber Subscribers
     * @param page 页码
     */
    void getEquipments(Subscriber<EquipmentData> subscriber, int page);

    /**
     * 获取装备详情
     * @param subscriber Subscribers
     * @param id id
     */
    void getEquipmentDetail(Subscriber<EquipmentDetailData> subscriber, int id);
}
