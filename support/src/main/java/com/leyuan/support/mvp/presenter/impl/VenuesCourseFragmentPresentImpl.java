package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.support.entity.data.CourseData;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.mvp.model.VenuesModel;
import com.leyuan.support.mvp.model.impl.VenuesModelImpl;
import com.leyuan.support.mvp.presenter.VenuesCourseFragmentPresent;
import com.leyuan.support.mvp.view.VenuesCourseFragmentView;

/**
 * 教练 - 课程
 * Created by song on 2016/8/30.
 */
public class VenuesCourseFragmentPresentImpl implements VenuesCourseFragmentPresent{

    private Context context;
    private VenuesModel venuesModel;
    private VenuesCourseFragmentView venuesCourseFragmentView;

    public VenuesCourseFragmentPresentImpl(Context context, VenuesCourseFragmentView venuesCourseFragmentView) {
        this.context = context;
        this.venuesCourseFragmentView = venuesCourseFragmentView;
        venuesModel = new VenuesModelImpl();
    }

    @Override
    public void getCourses(int id) {
        venuesModel.getCourses(new ProgressSubscriber<CourseData>(context,false) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()){
                    venuesCourseFragmentView.setCourses(courseData.getCourse());
                }else {
                    venuesCourseFragmentView.showNoCourseView();
                }
            }
        },id);
    }
}
