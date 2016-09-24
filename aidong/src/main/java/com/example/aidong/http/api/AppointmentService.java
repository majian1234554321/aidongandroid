package com.example.aidong.http.api;

import com.example.aidong.entity.BaseBean;
import com.example.aidong.entity.data.AppointmentData;
import com.example.aidong.entity.data.AppointmentDetailData;

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
