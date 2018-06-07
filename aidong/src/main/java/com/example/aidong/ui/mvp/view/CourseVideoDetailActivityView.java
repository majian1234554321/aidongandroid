package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CourseVideoBean;

import java.util.List;

/**
 * 课程视频
 * Created by song on 2017/4/26.
 */
public interface CourseVideoDetailActivityView {

    void updateRelateVideo(String title,List<CourseVideoBean> videoBeanList);

    void showLoadingView();

    void showErrorView();

    void showContentView();
}
