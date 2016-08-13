package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.CourseBean;
import com.leyuan.support.entity.CourseDetailBean;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.CourseService;
import com.leyuan.support.mvp.model.CourseModel;

import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public class CourseModelImpl implements CourseModel{
    private CourseService courseService;

    public CourseModelImpl() {
        courseService = RetrofitHelper.createApi(CourseService.class);
    }

    @Override
    public void getCourses(Subscriber<List<CourseBean>> subscriber, int cat, int day, int page) {
        courseService.getCourses(cat,day,page)
                .compose(RxHelper.<List<CourseBean>>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourseDetail(Subscriber<CourseDetailBean> subscriber, int id) {
        courseService.getCourseDetail(id)
                .compose(RxHelper.<CourseDetailBean>transform())
                .subscribe(subscriber);
    }
}
