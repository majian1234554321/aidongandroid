package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.data.CourseFilterData;
import com.leyuan.aidong.http.RetrofitVersionHelper;
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
        courseService = RetrofitVersionHelper.createVersionApi(CourseServiceNew.class);
    }

    public void getCourseFilterConfig(Subscriber<CourseFilterData> subscriber) {
        courseService.getCourseFilterConfig()
                .compose(RxHelper.<CourseFilterData>transform())
                .subscribe(subscriber);
    }



}
