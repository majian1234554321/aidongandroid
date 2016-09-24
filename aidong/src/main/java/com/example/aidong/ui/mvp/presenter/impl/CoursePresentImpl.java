package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.entity.CourseBean;
import com.example.aidong.entity.CourseDetailBean;
import com.example.aidong.entity.data.CourseData;
import com.example.aidong.http.subscriber.ProgressSubscriber;
import com.example.aidong.http.subscriber.RefreshSubscriber;
import com.example.aidong.http.subscriber.RequestMoreSubscriber;
import com.example.aidong.ui.mvp.model.CourseModel;
import com.example.aidong.ui.mvp.model.impl.CourseModelImpl;
import com.example.aidong.ui.mvp.presenter.CoursePresent;
import com.example.aidong.ui.mvp.view.CourseDetailActivityView;
import com.example.aidong.ui.mvp.view.CoursesActivityView;
import com.example.aidong.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程
 * Created by song on 2016/9/21.
 */
public class CoursePresentImpl  implements CoursePresent{
    private Context context;
    private CourseModel courseModel;

    private List<CourseBean> courseBeanList;
    private CoursesActivityView coursesActivityView;                //课程列表View层对象
    private CourseDetailActivityView courseDetailActivityView;      //课程详情View层对象

    public CoursePresentImpl(Context context, CoursesActivityView view) {
        this.context = context;
        this.coursesActivityView = view;
        courseBeanList = new ArrayList<>();
        if(courseModel == null){
            courseModel = new CourseModelImpl();
        }
    }

    public CoursePresentImpl(Context context, CourseDetailActivityView view) {
        this.context = context;
        this.courseDetailActivityView = view;
        if(courseModel == null){
            courseModel = new CourseModelImpl();
        }
    }

    @Override
    public void commendLoadData() {

    }

    @Override
    public void pullToRefreshData(int category, int day) {
        courseModel.getCourses(new RefreshSubscriber<CourseData>(context) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null){
                    courseBeanList = courseData.getCourse();
                }
                if(courseBeanList != null && courseBeanList.isEmpty()){
                    coursesActivityView.showEmptyView();
                    coursesActivityView.hideRecyclerView();
                }else {
                    coursesActivityView.hideEmptyView();
                    coursesActivityView.showRecyclerView();
                    coursesActivityView.updateRecyclerView(courseBeanList);
                }
            }
        },category,day, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int category, int day, int page) {
        courseModel.getCourses(new RequestMoreSubscriber<CourseData>(context,recyclerView,page) {
            @Override
            public void onNext(CourseData courseData) {

                if(courseData != null){
                    courseBeanList = courseData.getCourse();
                }

                if(courseBeanList != null && !courseBeanList.isEmpty()){
                    coursesActivityView.updateRecyclerView(courseBeanList);
                }

                //没有更多数据了显示到底提示
                if(courseBeanList != null && courseBeanList.size() < pageSize){
                    coursesActivityView.showEndFooterView();
                }
            }
        },category,day,pageSize);
    }

    @Override
    public void getCourseDetail(String id) {
        courseModel.getCourseDetail(new ProgressSubscriber<CourseDetailBean>(context) {
            @Override
            public void onNext(CourseDetailBean courseDetailBean) {
                courseDetailActivityView.setCourseDetail(courseDetailBean);
            }
        },id);
    }
}
