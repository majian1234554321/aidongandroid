package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.support.entity.CourseDetailBean;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.mvp.model.CourseModel;
import com.leyuan.support.mvp.model.impl.CourseModelImpl;
import com.leyuan.support.mvp.presenter.CourseDetailActivityPresent;
import com.leyuan.support.mvp.view.CourseDetailActivityView;

/**
 * 课程详情
 * Created by song on 2016/8/17.
 */
public class CourseDetailActivityPresentImpl implements CourseDetailActivityPresent{

    private Context context;
    private CourseDetailActivityView courseDetailActivityView;
    private CourseModel courseModel;

    public CourseDetailActivityPresentImpl(Context context, CourseDetailActivityView courseDetailActivityView) {
        this.context = context;
        this.courseDetailActivityView = courseDetailActivityView;
        courseModel = new CourseModelImpl();
    }

    @Override
    public void getCourseDetail(String id) {
        courseModel.getCourseDetail(new ProgressSubscriber<CourseDetailBean>(context) {
            @Override
            public void onNext(CourseDetailBean courseDetailBean) {
                courseDetailActivityView.setCourseDetail(courseDetailBean);
            }
        },id);
    }
}
