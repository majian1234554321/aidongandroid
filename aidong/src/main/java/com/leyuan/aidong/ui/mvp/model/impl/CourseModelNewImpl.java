package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.course.CourseDataNew;
import com.leyuan.aidong.entity.course.CourseDetailDataNew;
import com.leyuan.aidong.entity.data.CourseFilterData;
import com.leyuan.aidong.http.RetrofitCourseHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CourseServiceNew;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public class CourseModelNewImpl {
    private Context context;
    private CourseServiceNew courseService;

    public CourseModelNewImpl(Context context) {
        this.context = context;
        courseService = RetrofitCourseHelper.createApi(CourseServiceNew.class);
    }

    public void getCourseFilterConfig(Subscriber<CourseFilterData> subscriber) {
        courseService.getCourseFilterConfig()
                .compose(RxHelper.<CourseFilterData>transform())
                .subscribe(subscriber);
    }

    public void getCourseList(Subscriber<CourseDataNew> subscriber, String store, String course, String time,
                              String date,String page) {
        courseService.getCourseList(store,course,time,date,page)
                .compose(RxHelper.<CourseDataNew>transform())
                .subscribe(subscriber);
    }

    public void getCourseDetail(Subscriber<CourseDetailDataNew> subscriber, String id){
        courseService.getCourseDetail(id)
                .compose(RxHelper.<CourseDetailDataNew>transform())
                .subscribe(subscriber);
    }


}
