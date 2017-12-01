package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseAppointBean;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/23.
 */
public interface AppointmentCourseListView {

    void onFirstPageCourseAppointList(ArrayList<CourseAppointBean> appointment);

    void onLoadMoreCourseAppointList(ArrayList<CourseAppointBean> appointment);

    void oncancelCourseAppointResult(BaseBean courseAppointResult);

    void onDeleteCourseAppoint(boolean courseAppointResult);

    void onCancelCourseQueue(BaseBean baseBean);

    void onDeleteCourseQueue(boolean courseQueueResult);
}
