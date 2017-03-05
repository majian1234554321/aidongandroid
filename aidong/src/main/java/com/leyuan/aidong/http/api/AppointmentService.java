package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.AppointmentData;
import com.leyuan.aidong.entity.data.AppointmentDetailData;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("mine/appointments/{id}/cancel")
    Observable<BaseBean> cancelAppointment(@Path("id") String id);

    @POST("mine/appointments/{id}/confirm")
    Observable<BaseBean> confirmAppointment(@Path("id") String id);

    @DELETE("mine/appointments/{id}")
    Observable<BaseBean> deleteAppointment(@Path("id") String id);
}
