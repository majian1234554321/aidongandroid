package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.course.CourseQueueBean;

import java.util.List;

/**
 * Created by user on 2017/11/24.
 */
public interface ConfirmCourseQueueView {
    void onGetsubmitCourseQueue(CourseQueueBean queue);

    void onGetCourseAvaliableCoupons(List<CouponBean> coupon);
}
