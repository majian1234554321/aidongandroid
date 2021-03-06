package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.DistrictBean;
import com.example.aidong .entity.CategoryBean;

import java.util.List;

/**
 * 课程
 * Created by song on 2016/11/14.
 */
public interface CourseActivityView {

    /**
     * 设置课程分类
     */
    void setCategory(List<CategoryBean> categoryBeanList);

    /**
     * 设置热门商圈
     */
    void setBusinessCircle(List<DistrictBean> circleBeanList);

    void setScrollPosition(String date);
}
