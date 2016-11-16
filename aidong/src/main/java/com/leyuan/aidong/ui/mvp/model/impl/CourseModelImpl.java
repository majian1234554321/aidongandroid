package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BusinessCircleBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CourseService;
import com.leyuan.aidong.ui.mvp.model.CourseModel;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public class CourseModelImpl implements CourseModel {
    private Context context;
    private CourseService courseService;

    public CourseModelImpl(Context context) {
        this.context = context;
        courseService = RetrofitHelper.createApi(CourseService.class);
    }

    @Override
    public List<String> getDate() {
        return null;
    }

    @Override
    public List<CategoryBean> getCategory() {
        return SystemInfoUtils.getCourseCategory(context);
    }

    @Override
    public List<BusinessCircleBean> getBusinessCircle() {
        return SystemInfoUtils.getLandmark(context);
    }

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
