package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BusinessCircleBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.entity.data.CourseData;

import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public interface CourseModel {

    /**
     * 获取课程分类信息
     * @return 课程分类
     */
    List<CategoryBean> getCategory();

    /**
     * 获取商圈信息
     * @return 商圈信息
     */
    List<BusinessCircleBean> getBusinessCircle();

    /**
     * 获取课程列表
     * @param subscriber Subscriber
     * @param cat 类型
     * @param day 从当前开始向后天数
     * @param page 页码
     */
    void getCourses(Subscriber<CourseData> subscriber, String cat, String day, int page);

    /**
     * 获取课程详情
     * @param subscriber Subscriber
     * @param id 课程id
     */
    void getCourseDetail(Subscriber<CourseDetailBean> subscriber, String id);
}
