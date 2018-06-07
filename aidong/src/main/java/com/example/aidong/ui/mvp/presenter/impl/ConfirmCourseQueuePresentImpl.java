package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.course.CourseQueueResult;
import com.example.aidong .entity.data.CouponData;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .ui.mvp.model.impl.CourseModelNewImpl;
import com.example.aidong .ui.mvp.view.ConfirmCourseQueueView;

/**
 * Created by user on 2017/11/24.
 */
public class ConfirmCourseQueuePresentImpl {

    Context context;

    ConfirmCourseQueueView callback;
    CourseModelNewImpl model;

    public ConfirmCourseQueuePresentImpl(Context context, ConfirmCourseQueueView callback) {
        this.context = context;
        this.callback = callback;
        model = new CourseModelNewImpl(context);
    }

    public void submitCourseQueue( String courseId, String coupon_id){
        model.submitCourseQueue(new BaseSubscriber<CourseQueueResult>(context) {
            @Override
            public void onNext(CourseQueueResult courseQueueResult) {
                if(courseQueueResult != null && courseQueueResult.getQueue() != null){
                    callback.onGetsubmitCourseQueue(courseQueueResult.getQueue());
                }else  if(courseQueueResult != null && courseQueueResult.getAppointment() != null){
                    callback.onQueueAppointSuccess(courseQueueResult.getAppointment());
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                callback.onGetsubmitCourseQueue(null);
            }
        },courseId,coupon_id);
    }

    public void getCourseAvaliableCoupons(String id) {

        model.getCourseAvaliableCoupons(new BaseSubscriber<CouponData>(context) {
            @Override
            public void onNext(CouponData couponData) {
                callback.onGetCourseAvaliableCoupons(couponData.getCoupon());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                callback.onGetCourseAvaliableCoupons(null);
            }
        }, id);
    }

}
