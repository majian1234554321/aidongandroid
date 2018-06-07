package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.course.CourseAppointResult;

/**
 * Created by user on 2017/11/24.
 */
public interface AppointmentCourseDetailView {
    void onGetAppointDetailResult(CourseAppointResult courseAppointResult);
    void oncancelCourseAppointResult(BaseBean courseAppointResult);

    void onDeleteCourseAppoint(boolean courseAppointResult);
}
