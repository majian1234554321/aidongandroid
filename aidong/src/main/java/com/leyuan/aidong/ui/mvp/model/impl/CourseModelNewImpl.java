package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseAppointListResult;
import com.leyuan.aidong.entity.course.CourseAppointResult;
import com.leyuan.aidong.entity.course.CourseDataNew;
import com.leyuan.aidong.entity.course.CourseDetailDataNew;
import com.leyuan.aidong.entity.course.CourseQueueResult;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.data.CourseFilterData;
import com.leyuan.aidong.http.RetrofitCourseHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CourseServiceNew;
import com.leyuan.aidong.ui.App;

import retrofit2.http.Path;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                              String date, String page,String idVlaue) {
        String mobile = App.getInstance().isLogin()?App.getInstance().getUser().getMobile():"";
        courseService.getCourseList(store, course, time, date, page,mobile,idVlaue)
                .compose(RxHelper.<CourseDataNew>transform())
                .subscribe(subscriber);
    }



    public void getCoachCourseList(Subscriber<CourseDataNew> subscriber, String mobile, String date) {
        courseService.getCoachCourseList(mobile,date)
                .compose(RxHelper.<CourseDataNew>transform())
                .subscribe(subscriber);
    }

    public void getCourseDetail(Subscriber<CourseDetailDataNew> subscriber, String id) {
        courseService.getCourseDetail(id)
                .compose(RxHelper.<CourseDetailDataNew>transform())
                .subscribe(subscriber);
    }

    public void getCourseAvaliableCoupons(Subscriber<CouponData> subscriber, String id) {
        courseService.getCourseAvaliableCoupons(id)
                .compose(RxHelper.<CouponData>transform())
                .subscribe(subscriber);
    }

    public void confirmAppointCourse(Subscriber<CourseAppointResult> subscriber, String courseId, String coupon_id,String seat) {
        courseService.confirmAppointCourse(courseId, coupon_id, seat)
                .compose(RxHelper.<CourseAppointResult>transform())
                .subscribe(subscriber);
    }


    public void lookCourseAppoint(Subscriber<CourseAppointResult> subscriber, String courseId) {
        courseService.lookCourseAppoint(courseId)
                .compose(RxHelper.<CourseAppointResult>transform())
                .subscribe(subscriber);
    }

    public void getCourseAppointList(Subscriber<CourseAppointListResult> subscriber, String list, String page) {
        courseService.getCourseAppointList(list, page)
                .compose(RxHelper.<CourseAppointListResult>transform())
                .subscribe(subscriber);
    }

    public void getCourseAppointDetail(Subscriber<CourseAppointResult> subscriber, String appointId) {
        courseService.getCourseAppointDetail(appointId)
                .compose(RxHelper.<CourseAppointResult>transform())
                .subscribe(subscriber);
    }

    public void cancelCourseAppoint(String appointId, Subscriber<BaseBean> subscriber) {
        courseService.cancelCourseAppoint(appointId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void deleteCourseAppoint(String appointId, Subscriber<BaseBean> subscriber) {
        courseService.deleteCourseAppoint(appointId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void submitCourseQueue(Subscriber<CourseQueueResult> subscriber, String courseId, String coupon_id) {
        courseService.submitCourseQueue(courseId, coupon_id)
                .compose(RxHelper.<CourseQueueResult>transform())
                .subscribe(subscriber);
    }


    public void getCourseQueueDetailFromCourse(Subscriber<CourseQueueResult> subscriber, String courseId) {
        courseService.getCourseQueueDetailFromCourse(courseId)
                .compose(RxHelper.<CourseQueueResult>transform())
                .subscribe(subscriber);
    }

    public void getCourseQueueDetailFromQueue(Subscriber<CourseQueueResult> subscriber, String queueId) {
        courseService.getCourseQueueDetailFromQueue(queueId)
                .compose(RxHelper.<CourseQueueResult>transform())
                .subscribe(subscriber);
    }

    public void cancelCourseQueue(String queueId,  Subscriber<BaseBean> subscriber) {
        courseService.cancelCourseQueue(queueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void deleteCourseQueue(@Path("id") String queueId, Subscriber<BaseBean> subscriber) {
        courseService.deleteCourseQueue(queueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
