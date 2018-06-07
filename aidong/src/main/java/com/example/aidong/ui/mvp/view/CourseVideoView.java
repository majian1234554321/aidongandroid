package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CourseVideoBean;

import java.util.List;

/**
 * Created by user on 2018/2/2.
 */
public interface CourseVideoView {
    void updateRelateVideo(String title, List<CourseVideoBean> videos);
}
