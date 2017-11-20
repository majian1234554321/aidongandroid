package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.data.CourseFilterData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelNewImpl;
import com.leyuan.aidong.utils.SharePrefUtils;

/**
 * 课程
 * Created by song on 2016/9/21.
 */
public class CoursePresentNewImpl {

    private Context context;

    CourseModelNewImpl courseModel;
    public CoursePresentNewImpl(Context context) {
        this.context = context;
        courseModel = new CourseModelNewImpl(context);
    }

    public void getCourseFilterConfig() {
        courseModel.getCourseFilterConfig(new BaseSubscriber<CourseFilterData>(context) {
            @Override
            public void onNext(CourseFilterData courseFilterData) {
                SharePrefUtils.putCourseFilterConfig(context,courseFilterData);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
