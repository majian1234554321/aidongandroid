package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.AppointmentData;
import com.example.aidong .entity.data.AppointmentDetailData;

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
    void getAppointmentDetail(Subscriber<AppointmentDetailData> subscriber, String id);

    /**
     * 取消预约
     * @param subscriber
     * @param id 订单id
     */
    void cancelAppointment(Subscriber<BaseBean> subscriber,String id);

    /**
     * 确认预约
     * @param subscriber
     * @param id 订单id
     */
    void confirmAppointment(Subscriber<BaseBean> subscriber,String id);

    /**
     * 删除预约
     * @param subscriber
     * @param id 订单id
     */
    void deleteAppointment(Subscriber<BaseBean> subscriber,String id);
}
