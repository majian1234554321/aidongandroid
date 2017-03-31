package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CouponBean;

import java.util.List;

/**
 * 预约课程
 * Created by song on 2017/3/9.
 */
public interface AppointCourseActivityView {

    void setCourseCouponResult(List<CouponBean> couponBeanList);
    void onFreeCourseAppointed();
}
