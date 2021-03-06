package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CourseBean;

import java.util.List;

/**
 * 搜索课程
 * Created by song on 2016/9/1.
 */
public interface SearchCourseFragmentView {

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();



    void onRecyclerViewRefresh(List<CourseBean> venuesBeanList);

    void onRecyclerViewLoadMore(List<CourseBean> venuesBeanList);

    void showEmptyView();
}
