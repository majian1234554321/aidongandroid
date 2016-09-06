package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.data.AppointmentData;
import com.leyuan.support.entity.data.AppointmentDetailData;

import rx.Subscriber;

/**
 * 预约
 * Created by song on 2016/9/1.
 */
public interface AppointmentModel {

    /**
     * 获取预约列表
     * @param subscriber Subscriber
     * @param type  全部 待参加 已参加
     * @param page 页码
     */
    void getAppointments(Subscriber<AppointmentData> subscriber, String type, int page);


    /**
     * 获取预约详情
     * @param subscriber Subscriber
     * @param id 订单id
     */
    void getAppointmentDetail(Subscriber<AppointmentDetailData> subscriber, int id);
}
