package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.course.CourseBeanNew;

import java.util.ArrayList;

/**
 * 课程列表
 * Created by song on 2016/11/1.
 */
public interface CourserFragmentView {


    void refreshRecyclerViewData(ArrayList<CourseBeanNew> courseList);

    void loadMoreRecyclerViewData(ArrayList<CourseBeanNew> courseList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();


   void showEmptyView();


}
