package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CourseDetailBeanOld;
import com.example.aidong .entity.course.CourseDetailBean;

/**
 * 课程详情
 * Created by song on 2016/8/17.
 */
public interface CourseDetailActivityView {

    /**
     * 获取课程详情
     * @param courseDetailBean 课程详情实体
     */
    void setCourseDetail(CourseDetailBean courseDetailBean);

    /**
     * 添加关注
     * @param baseBean
     */
    void addFollowResult(BaseBean baseBean);

    /**
     * 取消关注
     * @param baseBean
     */
    void cancelFollowResult(BaseBean baseBean);
}
