package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.entity.data.CoachData;
import com.leyuan.support.entity.data.CourseData;
import com.leyuan.support.entity.data.VenuesData;
import com.leyuan.support.entity.data.VenuesDetailData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.VenuesModel;
import com.leyuan.support.mvp.model.impl.VenuesModelImpl;
import com.leyuan.support.mvp.presenter.VenuesPresent;
import com.leyuan.support.mvp.view.DiscoverVenuesActivityView;
import com.leyuan.support.mvp.view.VenuesCoachFragmentView;
import com.leyuan.support.mvp.view.VenuesCourseFragmentView;
import com.leyuan.support.mvp.view.VenuesDetailFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆
 * Created by song on 2016/9/21.
 */
public class VenuesPresentImpl implements VenuesPresent{
    private Context context;
    private VenuesModel venuesModel;

    private List<VenuesBean> venuesBeanList;
    private DiscoverVenuesActivityView discoverVenuesActivityView;  //场馆列表View层对象
    private VenuesCoachFragmentView venuesCoachFragmentView;        //场馆教练View层对象
    private VenuesCourseFragmentView venuesCourseFragmentView;      //场馆课程View层对象
    private VenuesDetailFragmentView venuesDetailFragmentView;      //场馆详情View层对象

    public VenuesPresentImpl(Context context,DiscoverVenuesActivityView view) {
        this.context = context;
        this.discoverVenuesActivityView = view;
        venuesBeanList = new ArrayList<>();
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl();
        }
    }

    public VenuesPresentImpl(Context context, VenuesDetailFragmentView view) {
        this.context = context;
        this.venuesDetailFragmentView = view;
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl();
        }
    }

    public VenuesPresentImpl(Context context, VenuesCourseFragmentView view) {
        this.context = context;
        this.venuesCourseFragmentView = view;
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl();
        }
    }

    public VenuesPresentImpl(Context context, VenuesCoachFragmentView view) {
        this.context = context;
        this.venuesCoachFragmentView = view;
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl();
        }
    }

    @Override
    public void commonLoadData(SwitcherLayout switcherLayout) {
        venuesModel.getVenues(new CommonSubscriber<VenuesData>(switcherLayout) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesBeanList = venuesData.getGym();
                }
                if(!venuesBeanList.isEmpty()){
                    discoverVenuesActivityView.updateRecyclerView(venuesBeanList);
                }else{
                    discoverVenuesActivityView.showEmptyView();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData() {
        venuesModel.getVenues(new RefreshSubscriber<VenuesData>(context) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesBeanList = venuesData.getGym();
                }
                if(!venuesBeanList.isEmpty()){
                    discoverVenuesActivityView.updateRecyclerView(venuesBeanList);
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        venuesModel.getVenues(new RequestMoreSubscriber<VenuesData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesBeanList = venuesData.getGym();
                }
                if(!venuesBeanList.isEmpty()){
                    discoverVenuesActivityView.updateRecyclerView(venuesBeanList);
                }
                //没有更多数据了显示到底提示
                if(venuesBeanList.size() < pageSize){
                    discoverVenuesActivityView.showEndFooterView();
                }
            }
        },page);
    }

    @Override
    public void getVenuesDetail(int id) {
        venuesModel.getVenuesDetail(new ProgressSubscriber<VenuesDetailData>(context) {
            @Override
            public void onNext(VenuesDetailData venuesDetailData) {
                if(venuesDetailData != null && venuesDetailData.getGym() != null){
                    venuesDetailFragmentView.setVenuesDetail(venuesDetailData.getGym());
                }
            }
        },id);
    }

    @Override
    public void getCourses(int id) {
        venuesModel.getCourses(new ProgressSubscriber<CourseData>(context,false) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()){
                    venuesCourseFragmentView.setCourses(courseData.getCourse());
                }else {
                    venuesCourseFragmentView.showNoCourseView();
                }
            }
        },id);
    }

    @Override
    public void getCoaches(int id) {
        venuesModel.getCoaches(new ProgressSubscriber<CoachData>(context,false) {
            @Override
            public void onNext(CoachData coachData) {
                if(coachData != null && coachData.getCoach() != null && !coachData.getCoach().isEmpty()){
                    venuesCoachFragmentView.setCoaches(coachData.getCoach());
                }
            }
        },id);
    }
}
