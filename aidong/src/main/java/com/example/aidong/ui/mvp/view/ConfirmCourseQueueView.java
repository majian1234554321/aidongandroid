package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CouponBean;
import com.example.aidong .entity.course.CourseAppointBean;
import com.example.aidong .entity.course.CourseQueueBean;

import java.util.List;

/**
 * Created by user on 2017/11/24.
 */
public interface ConfirmCourseQueueView {
    void onGetsubmitCourseQueue(CourseQueueBean queue);

    void onGetCourseAvaliableCoupons(List<CouponBean> coupon);

    void onQueueAppointSuccess(CourseAppointBean appointment);
}
