package com.example.aidong.ui.mvp.model.impl;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.CourseDetailData;
import com.example.aidong .entity.CourseTypeListBean;
import com.example.aidong .entity.DistrictBean;
import com.example.aidong .entity.course.CourseDataNew;
import com.example.aidong .entity.data.AppointmentDetailData;
import com.example.aidong .entity.data.CourseVideoData;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.CourseService;
import com.example.aidong .ui.mvp.model.CourseModel;
import com.example.aidong .utils.SystemInfoUtils;

import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
@Deprecated
public class CourseModelImpl implements CourseModel {
    private Context context;
    private CourseService courseService;
    @Deprecated
    public CourseModelImpl(Context context) {
        this.context = context;
        courseService = RetrofitHelper.createApi(CourseService.class);
    }
    @Deprecated
    @Override
    public List<CategoryBean> getCategory() {
        return SystemInfoUtils.getCourseCategory(context);
    }
    @Deprecated
    @Override
    public List<DistrictBean> getBusinessCircle() {
        return SystemInfoUtils.getLandmark(context);
    }
    @Deprecated
    @Override
    public void getCourses(Subscriber<CourseDataNew> subscriber, String day, String cat, String landmark, int page) {
        courseService.getCourses(day,cat,landmark,page)
                .compose(RxHelper.<CourseDataNew>transform())
                .subscribe(subscriber);
    }
    @Deprecated
    @Override
    public void getCourseDetail(Subscriber<CourseDetailData> subscriber, String id) {
        courseService.getCourseDetail(id)
                .compose(RxHelper.<CourseDetailData>transform())
                .subscribe(subscriber);
    }
    @Deprecated
    @Override
    public void buyCourse(Subscriber<PayOrderData> subscriber, String id, @Nullable String couponId,
                          @Nullable String integral, String payType, String contactName, String contactMobile,String isVip) {
        courseService.buyCourse(id,couponId,integral,payType,contactName,contactMobile,isVip)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }
    @Deprecated
    @Override
    public void getCourseAppointDetail(Subscriber<AppointmentDetailData> subscriber, String id) {
        courseService.getCourseAppointDetail(id)
                .compose(RxHelper.<AppointmentDetailData>transform())
                .subscribe(subscriber);
    }
    @Deprecated
    @Override
    public void getCourseVideo(Subscriber<CourseVideoData> subscriber, String relate, String id, int page,String videoId) {
        courseService.getCourseVideo(id,relate,videoId,page)
                .compose(RxHelper.<CourseVideoData>transform())
                .subscribe(subscriber);
    }
    @Deprecated
    @Override
    public void getCourseVideoTypeList(Subscriber<CourseTypeListBean> subscriber) {
        courseService.getCourseVideoTypeList()
                .compose(RxHelper.<CourseTypeListBean>transform())
                .subscribe(subscriber);
    }
}
