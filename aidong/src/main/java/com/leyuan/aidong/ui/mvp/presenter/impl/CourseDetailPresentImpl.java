package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.course.CourseDetailDataNew;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelNewImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailViewNew;

/**
 * Created by user on 2017/11/22.
 */
public class CourseDetailPresentImpl {

    private CourseModelNewImpl model;
    private Context context;
    private CourseDetailViewNew callback;
    public CourseDetailPresentImpl(Context context,CourseDetailViewNew callback) {
        this.context = context;
        this.model = new CourseModelNewImpl(context);
        this.callback = callback;
    }

    public void getCourseDetail(String id){
        model.getCourseDetail(new BaseSubscriber<CourseDetailDataNew>(context) {
            @Override
            public void onNext(CourseDetailDataNew courseDetailDataNew) {
                callback.onGetCourseDetail(courseDetailDataNew.getTimetable());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                callback.onGetCourseDetail(null);
            }

        },id);
    }
}
