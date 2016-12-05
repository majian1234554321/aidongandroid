package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BusinessCircleBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.CourseDetailData;
import com.leyuan.aidong.entity.data.CourseData;
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
    public List<BusinessCircleBean> getBusinessCircle() {
        return SystemInfoUtils.getLandmark(context);
    }

    @Override
    public void getCourses(Subscriber<CourseData> subscriber, String cat, String day, int page) {
        courseService.getCourses(day,page)
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
    public void buyCourse(Subscriber<PayOrderData> subscriber, String id, String couponId,String integral,String payType, String contactName, String contactMobile) {
        courseService.buyCourse(id,couponId,integral,payType,contactName,contactMobile)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }
}
