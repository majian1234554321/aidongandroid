package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CourseVideoBean;

import java.util.List;

/**
 * 课程更多视频
 * Created by song on 2017/4/26.
 */
public interface RelatedVideoActivityView {

    void updateRecycler(List<CourseVideoBean> courseVideoBeanList);

    void showEmptyView();

    void showEndFooterView();

}
