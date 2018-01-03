package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.data.CourseFilterData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelNewImpl;
import com.leyuan.aidong.ui.mvp.view.CourseFilterCallback;
import com.leyuan.aidong.utils.RequestResponseCount;
import com.leyuan.aidong.utils.SharePrefUtils;

/**
 * 课程
 * Created by song on 2016/9/21.
 */
public class CourseConfigPresentImpl {

    private Context context;

    CourseModelNewImpl courseModel;
    private RequestResponseCount requestResponse;

    public CourseConfigPresentImpl(Context context) {
        this.context = context;
        courseModel = new CourseModelNewImpl(context);
    }

    public void setOnRequestResponse(RequestResponseCount requestResponse) {
        this.requestResponse = requestResponse;
    }

    public void getCourseFilterConfig() {
        courseModel.getCourseFilterConfig(new BaseSubscriber<CourseFilterData>(context) {
            @Override
            public void onNext(CourseFilterData courseFilterData) {
                if (courseFilterData != null)
                    SharePrefUtils.putCourseFilterConfig(context, courseFilterData.getFilter());
                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }
            }
        });
    }

    public void getLocalCourseFilterConfig(CourseFilterCallback callback) {
        callback.onGetCourseFilterConfig(SharePrefUtils.getCourseFilterConfig(context));
    }
}