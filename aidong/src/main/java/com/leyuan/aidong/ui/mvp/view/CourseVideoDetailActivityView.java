package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CourseVideoBean;

import java.util.List;

/**
 * 课程视频
 * Created by song on 2017/4/26.
 */
public interface CourseVideoDetailActivityView {

    void updateRelateVideo(List<CourseVideoBean> videoBeanList);
}
