package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong.entity.CourseDetailBean;
import com.example.aidong.entity.data.CourseData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.RxHelper;
import com.example.aidong.http.api.CourseService;
import com.example.aidong.ui.mvp.model.CourseModel;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public class CourseModelImpl implements CourseModel {
    private CourseService courseService;

    public CourseModelImpl() {
        courseService = RetrofitHelper.createApi(CourseService.class);
    }

   /* @Override
    public void getCourses(Subscriber<List<CourseBean>> subscriber, int cat, int day, int page) {
        courseService.getCourses(cat,day,page)
                .compose(RxHelper.<List<CourseBean>>transform())
                .subscribe(subscriber);
    }*/


    @Override
    public void getCourses(Subscriber<CourseData> subscriber, int cat, int day, int page) {
        courseService.getCourses(day,page)
                .compose(RxHelper.<CourseData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourseDetail(Subscriber<CourseDetailBean> subscriber, String id) {
        courseService.getCourseDetail(id)
                .compose(RxHelper.<CourseDetailBean>transform())
                .subscribe(subscriber);
    }
}
