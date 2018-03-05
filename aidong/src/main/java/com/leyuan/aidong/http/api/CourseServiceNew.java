package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseAppointListResult;
import com.leyuan.aidong.entity.course.CourseAppointResult;
import com.leyuan.aidong.entity.course.CourseDataNew;
import com.leyuan.aidong.entity.course.CourseDetailDataNew;
import com.leyuan.aidong.entity.course.CourseQueueResult;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.data.CourseFilterData;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 课程
 * Created by huyushuai on 2017/8/13.
 */
public interface CourseServiceNew {
    @GET("app/api/config")
    Observable<BaseBean<CourseFilterData>> getCourseFilterConfig();

    @GET("app/api/timetables")
    Observable<BaseBean<CourseDataNew>> getCourseList(@Query("store") String store,
                                                      @Query("course") String course,
                                                      @Query("time") String time,
                                                      @Query("date") String date,
                                                      @Query("page") String page,
                                                      @Query("mobile") String mobile);

    @GET("app/api/coach/{mobile}/timetables")
    Observable<BaseBean<CourseDataNew>> getCoachCourseList(@Path("mobile") String mobile);



    @GET("app/api/timetables/{id}")
    Observable<BaseBean<CourseDetailDataNew>> getCourseDetail(@Path("id") String id);


    @GET("app/api/timetables/{id}/coupons")
    Observable<BaseBean<CouponData>>  getCourseAvaliableCoupons(@Path("id") String courseId);

    @FormUrlEncoded
    @POST("app/api/timetables/{id}/appointment")
    Observable<BaseBean<CourseAppointResult>> confirmAppointCourse(@Path("id") String courseId,
                                                                   @Field("coupon_id") String coupon_id,
                                                                   @Field("seat") String seat);

    @GET("app/api/timetables/{id}/appointment")
    Observable<BaseBean<CourseAppointResult>> lookCourseAppoint(@Path("id") String courseId);


    /**
     *
     * @param list history-历史课程 queued-排队课程 appointed-预约课程
     * @param page
     * @return
     */
    @GET("app/api/appointments")
    Observable<BaseBean<CourseAppointListResult>> getCourseAppointList(@Query("list") String list,
                                                                       @Query("page") String page);

    @GET("app/api/appointments/{id}")
    Observable<BaseBean<CourseAppointResult>> getCourseAppointDetail(@Path("id") String appointId);

    @DELETE("app/api/appointments/{id}")
    Observable<BaseBean> cancelCourseAppoint(@Path("id") String appointId);


    @POST("app/api/appointments/{id}/hidden")
    Observable<BaseBean> deleteCourseAppoint(@Path("id") String appointId);


    @FormUrlEncoded
    @POST("app/api/timetables/{id}/queue")
    Observable<BaseBean<CourseQueueResult>> submitCourseQueue(@Path("id") String courseId,
                                                              @Field("coupon_id") String coupon_id);

    @GET("app/api/timetables/{id}/queue")
    Observable<BaseBean<CourseQueueResult>> getCourseQueueDetailFromCourse(@Path("id") String courseId);

    @GET("app/api/queues/{id}")
    Observable<BaseBean<CourseQueueResult>> getCourseQueueDetailFromQueue(@Path("id") String queueId);

    @DELETE("app/api/queues/{id}")
    Observable<BaseBean> cancelCourseQueue(@Path("id") String queueId);

    @POST("app/api/queues/{id}/hidden")
    Observable<BaseBean> deleteCourseQueue(@Path("id") String queueId);
}
