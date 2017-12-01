package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseQueueBean;

/**
 * Created by user on 2017/11/27.
 */
public interface CourseQueueView {
    void ongetCourseQueueDetail(CourseQueueBean queue);

    void onCancelCourseQueue(BaseBean baseBean);

    void onDeleteCourseQueue(boolean b);
}
