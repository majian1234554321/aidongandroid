package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.CourseModel;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.view.CourseActivityView;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.CourserFragmentView;
import com.leyuan.aidong.utils.Constant;

import java.util.List;

/**
 * 课程
 * Created by song on 2016/9/21.
 */
public class CoursePresentImpl  implements CoursePresent{
    private Context context;
    private CourseModel courseModel;

    private List<CourseBean> courseBeanList;
    private CourserFragmentView courserFragmentView;
    private CourseActivityView coursesActivityView;                //课程列表View层对象
    private CourseDetailActivityView courseDetailActivityView;      //课程详情View层对象

    public CoursePresentImpl(Context context, CourseDetailActivityView view) {
        this.context = context;
        this.courseDetailActivityView = view;
        if(courseModel == null){
            courseModel = new CourseModelImpl(context);
        }
    }

    public CoursePresentImpl(Context context, CourserFragmentView view) {
        this.context = context;
        this.courserFragmentView = view;
        if(courseModel == null){
            courseModel = new CourseModelImpl(context);
        }
    }

    public CoursePresentImpl(Context context, CourseActivityView view) {
        this.context = context;
        this.coursesActivityView = view;
        if(courseModel == null){
            courseModel = new CourseModelImpl(context);
        }
    }

    @Override
    public void getCategory() {
        coursesActivityView.setCategory(courseModel.getCategory());
    }

    @Override
    public void getBusinessCircle() {
        coursesActivityView.setBusinessCircle(courseModel.getBusinessCircle());
    }

    @Override
    public void commendLoadData() {

    }

    @Override
    public void pullToRefreshData(String category, String day) {
        courseModel.getCourses(new RefreshSubscriber<CourseData>(context) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null && !courseData.getCourse().isEmpty()){
                    courserFragmentView.updateRecyclerView(courseData.getCourse());
                }
            }
        },category,day, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, String category, String day, int page) {
        courseModel.getCourses(new RequestMoreSubscriber<CourseData>(context,recyclerView,page) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null && !courseData.getCourse().isEmpty()){
                    courseBeanList = courseData.getCourse();
                }
                if(!courseBeanList.isEmpty()){
                    courserFragmentView.updateRecyclerView(courseBeanList);
                }
                //没有更多数据了显示到底提示
                if(courseBeanList != null && courseBeanList.size() < pageSize){
                    courserFragmentView.showEndFooterView();
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
