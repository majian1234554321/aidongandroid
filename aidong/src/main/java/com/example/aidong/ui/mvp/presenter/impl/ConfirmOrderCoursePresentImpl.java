package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.aidong .entity.course.CourseAppointBean;
import com.example.aidong .entity.course.CourseAppointResult;
import com.example.aidong .entity.data.CouponData;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.PayUtils;
import com.example.aidong .ui.mvp.model.impl.CourseModelNewImpl;
import com.example.aidong .ui.mvp.view.ConfirmOrderCourseView;
import com.example.aidong .utils.constant.PayType;

/**
 * Created by user on 2017/11/23.
 */
public class ConfirmOrderCoursePresentImpl {

    Context context;
    CourseModelNewImpl courseModel;
    ConfirmOrderCourseView callback;


    public ConfirmOrderCoursePresentImpl(Context context, ConfirmOrderCourseView callback) {
        this.context = context;
        courseModel = new CourseModelNewImpl(context);
        this.callback = callback;
    }

    public void getCourseAvaliableCoupons(String id) {

        courseModel.getCourseAvaliableCoupons(new BaseSubscriber<CouponData>(context) {
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

    public void confirmAppointCourse(String courseId, String coupon_id, String seat, @PayType final String payType, final PayInterface.PayListener listener) {
        courseModel.confirmAppointCourse(

                new BaseSubscriber<CourseAppointResult>(context) {

                    @Override
                    public void onNext(CourseAppointResult courseAppointResult) {

                        callback.onCourseAppointResult(courseAppointResult);
                        if (courseAppointResult.getAppointment() != null &&
                                TextUtils.equals(courseAppointResult.getAppointment().getOrder_status(),CourseAppointBean.paid)){
//                                courseAppointResult.getAppointment().getOrder_status().equals(CourseAppointBean.paid)) {
                            listener.onFree();
                        } else {
                            PayUtils.pay(context, payType, courseAppointResult.getPayment(), listener);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
//                        listener.onFail("0",null);
                        callback.onCourseAppointResult(null);
                    }
                }
                , courseId, coupon_id, seat);
    }

}
