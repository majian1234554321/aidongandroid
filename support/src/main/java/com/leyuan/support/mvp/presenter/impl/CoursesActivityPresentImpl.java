package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.CourseBean;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.CourseModel;
import com.leyuan.support.mvp.model.impl.CourseModelImpl;
import com.leyuan.support.mvp.presenter.CoursesActivityPresent;
import com.leyuan.support.mvp.view.CoursesActivityView;
import com.leyuan.support.util.Constant;

import java.util.List;

/**
 * 课程列表
 * Created by song on 2016/8/13.
 */
public class CoursesActivityPresentImpl implements CoursesActivityPresent{
    private Context context;
    private CoursesActivityView coursesActivityView;
    private CourseModel courseModel;

    public CoursesActivityPresentImpl(Context context, CoursesActivityView coursesActivityView) {
        this.context = context;
        this.coursesActivityView = coursesActivityView;
        courseModel = new CourseModelImpl();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView,int category,int day) {
       courseModel.getCourses(new RefreshSubscriber<List<CourseBean>>(context,recyclerView) {
           @Override
           public void onNext(List<CourseBean> courseBeanList) {
               if(courseBeanList != null && courseBeanList.isEmpty()){
                   coursesActivityView.showEmptyView();
                   coursesActivityView.hideRecyclerView();
               }else {
                   coursesActivityView.hideEmptyView();
                   coursesActivityView.showRecyclerView();
                   coursesActivityView.updateRecyclerView(courseBeanList);
               }
           }
       },category,day,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int category,int day,int page) {
        courseModel.getCourses(new RequestMoreSubscriber<List<CourseBean>>(context,recyclerView,page) {
            @Override
            public void onNext(List<CourseBean> courseBeanList) {
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
}
