package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.ui.mvp.model.impl.CourseModelNewImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentCourseDetailView;

/**
 * Created by user on 2017/11/24.
 */
@Deprecated
public class AppointmentCourseDetailPresenterImpl {
    private final CourseModelNewImpl model;
    Context context;
    AppointmentCourseDetailView callback;

    public AppointmentCourseDetailPresenterImpl(Context context, AppointmentCourseDetailView callback) {
        this.context = context;
        this.callback = callback;
        model = new CourseModelNewImpl(context);
    }
//
//    @Deprecated
//    public void getCourseAppointDetail( String appointId){
//        model.getCourseAppointDetail(new BaseSubscriber<CourseAppointResult>(context) {
//            @Override
//            public void onNext(CourseAppointResult courseAppointResult) {
//                callback.onGetAppointDetailResult(courseAppointResult);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                callback.onGetAppointDetailResult(null);
//            }
//        },appointId);
//    }
//    @Deprecated
//    public void checkCourseAppoint(String courseId) {
//        model.lookCourseAppoint(new BaseSubscriber<CourseAppointResult>(context) {
//            @Override
//            public void onNext(CourseAppointResult courseAppointResult) {
//                callback.onGetAppointDetailResult(courseAppointResult);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                callback.onGetAppointDetailResult(null);
//            }
//        },courseId);
//    }
}
