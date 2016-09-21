package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.AppointmentData;
import com.leyuan.support.entity.data.AppointmentDetailData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 预约
 * Created by song on 2016/9/2.
 */
public interface AppointmentService {

    @GET("mine/appointments")
    Observable<BaseBean<AppointmentData>> getAppointments(@Query("type") String type, @Query("page") int page);

    @GET("mine/appointments/{id}")
    Observable<BaseBean<AppointmentDetailData>> getAppointmentDetail(@Path("id") String id);
}
