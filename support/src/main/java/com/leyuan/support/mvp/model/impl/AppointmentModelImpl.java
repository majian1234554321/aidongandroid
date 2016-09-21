package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.data.AppointmentData;
import com.leyuan.support.entity.data.AppointmentDetailData;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.AppointmentService;
import com.leyuan.support.mvp.model.AppointmentModel;

import rx.Subscriber;

/**
 * 预约
 * Created by song on 2016/9/2.
 */
public class AppointmentModelImpl implements AppointmentModel{
    private AppointmentService appointmentService;

    public AppointmentModelImpl() {
        appointmentService = RetrofitHelper.createApi(AppointmentService.class);
    }

    @Override
    public void getAppointments(Subscriber<AppointmentData> subscriber, String type, int page) {
        appointmentService.getAppointments(type,page)
                .compose(RxHelper.<AppointmentData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getAppointmentDetail(Subscriber<AppointmentDetailData> subscriber, String id) {
        appointmentService.getAppointmentDetail(id)
                .compose(RxHelper.<AppointmentDetailData>transform())
                .subscribe(subscriber);
    }
}
