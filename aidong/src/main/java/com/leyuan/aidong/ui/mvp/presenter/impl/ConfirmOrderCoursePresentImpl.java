package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.leyuan.aidong.entity.course.CourseAppointBean;
import com.leyuan.aidong.entity.course.CourseAppointResult;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelNewImpl;
import com.leyuan.aidong.ui.mvp.view.ConfirmOrderCourseView;
import com.leyuan.aidong.utils.constant.PayType;

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
