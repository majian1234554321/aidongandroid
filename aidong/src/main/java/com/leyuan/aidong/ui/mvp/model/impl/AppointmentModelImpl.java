package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.AppointmentData;
import com.leyuan.aidong.entity.data.AppointmentDetailData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.AppointmentService;
import com.leyuan.aidong.ui.mvp.model.AppointmentModel;

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
