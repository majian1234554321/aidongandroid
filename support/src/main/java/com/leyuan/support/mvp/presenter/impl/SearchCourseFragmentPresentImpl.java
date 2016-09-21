package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.CourseBean;
import com.leyuan.support.entity.data.CourseData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.SearchModel;
import com.leyuan.support.mvp.model.impl.SearchModelImpl;
import com.leyuan.support.mvp.presenter.SearchPresent;
import com.leyuan.support.mvp.view.SearchCourseFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索课程
 * Created by song on 2016/9/18.
 */
public class SearchCourseFragmentPresentImpl implements SearchPresent {
    private Context context;
    private SearchModel searchModel;
    private SearchCourseFragmentView searchView;
    private List<CourseBean> courseList;

    public SearchCourseFragmentPresentImpl(Context context, SearchCourseFragmentView searchView) {
        this.context = context;
        this.searchView = searchView;
        searchModel = new SearchModelImpl();
        courseList = new ArrayList<>();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchCourse(new CommonSubscriber<CourseData>(switcherLayout) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null){
                    courseList = courseData.getCourse();
                }
                if(courseList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else {
                    switcherLayout.showContentLayout();
                    searchView.updateRecyclerView(courseList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String keyword) {
        searchModel.searchCourse(new RefreshSubscriber<CourseData>(context) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null){
                    courseList = courseData.getCourse();
                }
                if(!courseList.isEmpty()){
                    searchView.updateRecyclerView(courseList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchCourse(new RequestMoreSubscriber<CourseData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null){
                    courseList = courseData.getCourse();
                }
                if(!courseList.isEmpty()){
                    searchView.updateRecyclerView(courseList);
                }
                if(courseList.size() < pageSize){
                    searchView.showEndFooterView();
                }
            }
        },keyword,page);
    }
}
