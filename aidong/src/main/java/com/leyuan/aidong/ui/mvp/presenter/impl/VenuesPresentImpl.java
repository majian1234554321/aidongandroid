package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.entity.data.CoachData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.data.VenuesDetailData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.activity.home.SelfDeliveryVenuesActivity;
import com.leyuan.aidong.ui.mvp.model.VenuesModel;
import com.leyuan.aidong.ui.mvp.model.impl.VenuesModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.view.DiscoverVenuesActivityView;
import com.leyuan.aidong.ui.mvp.view.VenuesCoachFragmentView;
import com.leyuan.aidong.ui.mvp.view.VenuesCourseFragmentView;
import com.leyuan.aidong.ui.mvp.view.VenuesDetailFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

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
    private SelfDeliveryVenuesActivity selfDeliveryVenuesActivity;  //自提场馆列表View层对象

    public VenuesPresentImpl(Context context,DiscoverVenuesActivityView view) {
        this.context = context;
        this.discoverVenuesActivityView = view;
        venuesBeanList = new ArrayList<>();
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl(context);
        }
    }

    public VenuesPresentImpl(Context context, VenuesDetailFragmentView view) {
        this.context = context;
        this.venuesDetailFragmentView = view;
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl(context);
        }
    }

    public VenuesPresentImpl(Context context, VenuesCourseFragmentView view) {
        this.context = context;
        this.venuesCourseFragmentView = view;
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl(context);
        }
    }

    public VenuesPresentImpl(Context context, VenuesCoachFragmentView view) {
        this.context = context;
        this.venuesCoachFragmentView = view;
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl(context);
        }
    }

    public VenuesPresentImpl(Context context, SelfDeliveryVenuesActivity view) {
        this.context = context;
        this.selfDeliveryVenuesActivity = view;
        if(venuesModel == null){
            venuesModel = new VenuesModelImpl(context);
        }
    }

    @Override
    public void getGymBrand() {
        if(discoverVenuesActivityView != null){
            discoverVenuesActivityView.setGymBrand(venuesModel.getGymBrand());
        }
        if(selfDeliveryVenuesActivity != null){
            selfDeliveryVenuesActivity.setGymBrand(venuesModel.getGymBrand());
        }

    }

    @Override
    public void getBusinessCircle() {
        if(discoverVenuesActivityView != null){
            discoverVenuesActivityView.setBusinessCircle(venuesModel.getBusinessCircle());
        }
        if(selfDeliveryVenuesActivity != null){
            selfDeliveryVenuesActivity.setBusinessCircle(venuesModel.getBusinessCircle());
        }
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        venuesModel.getVenues(new CommonSubscriber<VenuesData>(switcherLayout) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesBeanList = venuesData.getGym();
                }
                if(!venuesBeanList.isEmpty()){
                    switcherLayout.showContentLayout();
                    discoverVenuesActivityView.updateRecyclerView(venuesBeanList);
                }else{
                    switcherLayout.showEmptyLayout();
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
    public void getVenuesDetail(final SwitcherLayout switcherLayout, String id) {
      /* venuesModel.getVenuesDetail(new ProgressSubscriber<VenuesDetailData>(context) {
            @Override
            public void onNext(VenuesDetailData venuesDetailData) {
                if(venuesDetailData != null && venuesDetailData.getBrand() != null){
                    venuesDetailFragmentView.setVenuesDetail(venuesDetailData.getBrand());
                }
            }
        },id);*/
      venuesModel.getVenuesDetail(new CommonSubscriber<VenuesDetailData>(switcherLayout) {
            @Override
            public void onNext(VenuesDetailData venuesDetailData) {
                if(venuesDetailData != null && venuesDetailData.getGym() != null){
                    switcherLayout.showContentLayout();
                    venuesDetailFragmentView.setVenuesDetail(venuesDetailData.getGym());
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },id);
    }

    @Override
    public void getCourses(final SwitcherLayout switcherLayout,String id) {
        venuesModel.getCourses(new CommonSubscriber<CourseData>(switcherLayout) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()){
                    switcherLayout.showContentLayout();
                    venuesCourseFragmentView.setCourses(courseData.getCourse());
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },id);
    }

    @Override
    public void getCoaches(final SwitcherLayout switcherLayout,String id) {
       /* venuesModel.getCoaches(new ProgressSubscriber<CoachData>(context) {
            @Override
            public void onNext(CoachData coachData) {
                if(coachData != null && coachData.getCoach() != null && !coachData.getCoach().isEmpty()){
                    venuesCoachFragmentView.setCoaches(coachData.getCoach());
                }
            }
        },id);*/
        venuesModel.getCoaches(new CommonSubscriber<CoachData>(switcherLayout) {
            @Override
            public void onNext(CoachData coachData) {
                if(coachData != null && coachData.getCoach() != null && !coachData.getCoach().isEmpty()){
                    switcherLayout.showContentLayout();
                    venuesCoachFragmentView.setCoaches(coachData.getCoach());
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },id);
    }
}
