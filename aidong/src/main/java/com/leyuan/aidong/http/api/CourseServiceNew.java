package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseDataNew;
import com.leyuan.aidong.entity.course.CourseDetailDataNew;
import com.leyuan.aidong.entity.data.CourseFilterData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 课程
 * Created by huyushuai on 2017/8/13.
 */
public interface CourseServiceNew {
    @GET("app/api/config")
    Observable<BaseBean<CourseFilterData>> getCourseFilterConfig();

    @GET("app/api/timetable/")
    Observable<BaseBean<CourseDataNew>> getCourseList(@Query("company") String company,
                                                      @Query("store") String store,
                                                      @Query("course") String course,
                                                      @Query("time") String time);

    @GET("app/api/timetable/{id}")
    Observable<BaseBean<CourseDetailDataNew>> getCourseDetail();


}
