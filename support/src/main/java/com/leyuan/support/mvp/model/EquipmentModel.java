package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.EquipmentBean;
import com.leyuan.support.entity.EquipmentDetailBean;

import java.util.List;

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
    void getEquipments(Subscriber<List<EquipmentBean>> subscriber, int page);

    /**
     * 获取装备详情
     * @param subscriber Subscribers
     * @param id id
     */
    void getEquipmentDetail(Subscriber<EquipmentDetailBean> subscriber, int id);
}
