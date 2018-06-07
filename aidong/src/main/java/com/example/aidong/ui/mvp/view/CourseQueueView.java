package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.course.CourseQueueBean;

/**
 * Created by user on 2017/11/27.
 */
public interface CourseQueueView {
    void ongetCourseQueueDetail(CourseQueueBean queue);

    void onCancelCourseQueue(BaseBean baseBean);

    void onDeleteCourseQueue(boolean b);
}
