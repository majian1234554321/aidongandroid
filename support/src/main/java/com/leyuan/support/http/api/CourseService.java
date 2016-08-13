package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.CourseBean;
import com.leyuan.support.entity.CourseDetailBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public interface CourseService {
    @GET("market/courses")
    Observable<BaseBean<List<CourseBean>>> getCourses(@Query("cat") int category, @Query("day") int day, @Query("page") int page);

    @GET("market/courses/{id}")
    Observable<BaseBean<CourseDetailBean>> getCourseDetail(@Path("id") int id);
}
