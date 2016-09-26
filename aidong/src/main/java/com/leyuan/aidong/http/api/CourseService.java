package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.entity.data.CourseData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public interface CourseService {
    //@GET("market/courses")
    //Observable<BaseBean<CourseData>> getCourses(@Query("cat") int category, @Query("day") int day, @Query("page") int page);

    @GET("market/courses")
    Observable<BaseBean<CourseData>> getCourses(@Query("day") int day, @Query("page") int page);

    @GET("market/courses/{id}")
    Observable<BaseBean<CourseDetailBean>> getCourseDetail(@Path("id") String id);
}
