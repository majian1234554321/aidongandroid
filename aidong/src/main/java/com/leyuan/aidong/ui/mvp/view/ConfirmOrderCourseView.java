package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.course.CourseAppointBean;

import java.util.List;

/**
 * Created by user on 2017/11/23.
 */
public interface ConfirmOrderCourseView {
    void onGetCourseAvaliableCoupons(List<CouponBean> coupon);

    void onCourseAppointResult(CourseAppointBean appointment);
}
