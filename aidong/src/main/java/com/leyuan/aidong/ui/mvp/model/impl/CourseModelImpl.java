package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;
import android.support.annotation.Nullable;

import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.CourseDetailData;
import com.leyuan.aidong.entity.data.AppointmentDetailData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.CourseVideoData;
import com.leyuan.aidong.entity.data.PayOrderData;
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
    public List<CategoryBean> getCategory() {
        return SystemInfoUtils.getCourseCategory(context);
    }

    @Override
    public List<DistrictBean> getBusinessCircle() {
        return SystemInfoUtils.getLandmark(context);
    }

    @Override
    public void getCourses(Subscriber<CourseData> subscriber, String day, String cat, String landmark, int page) {
        courseService.getCourses(day,cat,landmark,page)
                .compose(RxHelper.<CourseData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourseDetail(Subscriber<CourseDetailData> subscriber, String id) {
        courseService.getCourseDetail(id)
                .compose(RxHelper.<CourseDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void buyCourse(Subscriber<PayOrderData> subscriber, String id, @Nullable String couponId,
                          @Nullable String integral, String payType, String contactName, String contactMobile) {
        courseService.buyCourse(id,couponId,integral,payType,contactName,contactMobile)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourseAppointDetail(Subscriber<AppointmentDetailData> subscriber, String id) {
        courseService.getCourseAppointDetail(id)
                .compose(RxHelper.<AppointmentDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourseVideo(Subscriber<CourseVideoData> subscriber, String relate, String id, int page) {
        courseService.getCourseVideo(id,relate,page)
                .compose(RxHelper.<CourseVideoData>transform())
                .subscribe(subscriber);
    }
}
