package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CouponBean;
import com.example.aidong .entity.course.CourseAppointResult;

import java.util.List;

/**
 * Created by user on 2017/11/23.
 */
public interface ConfirmOrderCourseView {
    void onGetCourseAvaliableCoupons(List<CouponBean> coupon);

    void onCourseAppointResult(CourseAppointResult appointment);
}
