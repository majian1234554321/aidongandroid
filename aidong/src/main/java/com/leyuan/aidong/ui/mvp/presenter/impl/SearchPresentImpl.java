package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.entity.FoodBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.FoodData;
import com.leyuan.aidong.entity.data.UserData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.SearchModel;
import com.leyuan.aidong.ui.mvp.model.impl.SearchModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.view.SearchActivityView;
import com.leyuan.aidong.ui.mvp.view.SearchCampaignFragmentView;
import com.leyuan.aidong.ui.mvp.view.SearchCourseFragmentView;
import com.leyuan.aidong.ui.mvp.view.SearchFoodFragmentView;
import com.leyuan.aidong.ui.mvp.view.SearchUserFragmentView;
import com.leyuan.aidong.ui.mvp.view.SearchVenuesFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 * Created by song on 2016/9/21.
 */
public class SearchPresentImpl implements SearchPresent{
    private Context context;
    private SearchModel searchModel;

    private List<CampaignBean> campaignList;
    private List<CourseBean> courseList;
    private List<FoodBean> foodList;
    private List<UserBean> userList;
    private List<VenuesBean> venuesList;

    private SearchActivityView searchActivityView;  //搜索View层对象
    private SearchCampaignFragmentView campaignView;//搜索活动View层对象
    private SearchCourseFragmentView courseView;    //搜索课程View层对象
    private SearchFoodFragmentView foodView;        //搜索健康餐饮View层对象
    private SearchUserFragmentView userView;        //搜索用户View层对象
    private SearchVenuesFragmentView venuesView;    //搜索场馆View层对象

    public SearchPresentImpl(Context context, SearchActivityView view) {
        this.context = context;
        this.searchActivityView = view;
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    public SearchPresentImpl(Context context, SearchCampaignFragmentView view) {
        this.context = context;
        this.campaignView = view;
        campaignList = new ArrayList<>();
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    public SearchPresentImpl(Context context, SearchCourseFragmentView view) {
        this.context = context;
        this.courseView = view;
        courseList = new ArrayList<>();
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    public SearchPresentImpl(Context context, SearchFoodFragmentView view) {
        this.context = context;
        this.foodView = view;
        foodList = new ArrayList<>();
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    public SearchPresentImpl(Context context, SearchUserFragmentView view) {
        this.context = context;
        this.userView = view;
        userList = new ArrayList<>();
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    public SearchPresentImpl(Context context, SearchVenuesFragmentView view) {
        this.context = context;
        this.venuesView = view;
        venuesList = new ArrayList<>();
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    @Override
    public void getSearchHistory() {
        searchActivityView.setHistory(searchModel.getSearchHistory());
    }

    @Override
    public void insertHistory(String keyword) {
        searchModel.insertSearchHistory(keyword);
    }

    @Override
    public void commonLoadCampaignData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchCampaign(new CommonSubscriber<CampaignData>(switcherLayout) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData != null){
                    campaignList = campaignData.getCampaign();
                }
                if(campaignList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else {
                    switcherLayout.showContentLayout();
                    campaignView.updateRecyclerView(campaignList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void commonLoadCourseData(final SwitcherLayout switcherLayout, String keyword) {
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
                    courseView.updateRecyclerView(courseList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void commonLoadFoodData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchFood(new CommonSubscriber<FoodData>(switcherLayout) {
            @Override
            public void onNext(FoodData foodData) {
                if(foodData != null){
                    foodList = foodData.getFood();
                }
                if(foodList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else {
                    switcherLayout.showContentLayout();
                    foodView.updateRecyclerView(foodList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void commonUserData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchUser(new CommonSubscriber<UserData>(switcherLayout) {
            @Override
            public void onNext(UserData userData) {
                if(userData != null){
                    userList = userData.getUser();
                }
                if(userList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else {
                    switcherLayout.showContentLayout();
                    userView.updateRecyclerView(userList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void commonLoadVenuesData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchVenues(new CommonSubscriber<VenuesData>(switcherLayout) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesList = venuesData.getGym();
                }
                if(venuesList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else {
                    switcherLayout.showContentLayout();
                    venuesView.updateRecyclerView(venuesList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshCampaignData(String keyword) {
        searchModel.searchCampaign(new RefreshSubscriber<CampaignData>(context) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData != null){
                    campaignList = campaignData.getCampaign();
                }
                if(!campaignList.isEmpty()){
                    campaignView.updateRecyclerView(campaignList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshCourseData(String keyword) {
        searchModel.searchCourse(new RefreshSubscriber<CourseData>(context) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null){
                    courseList = courseData.getCourse();
                }
                if(!courseList.isEmpty()){
                    courseView.updateRecyclerView(courseList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshFoodData(String keyword) {
        searchModel.searchFood(new RefreshSubscriber<FoodData>(context) {
            @Override
            public void onNext(FoodData foodData) {
                if(foodData != null){
                    foodList = foodData.getFood();
                }
                if(!foodList.isEmpty()){
                    foodView.updateRecyclerView(foodList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshUserData(String keyword) {
        searchModel.searchUser(new RefreshSubscriber<UserData>(context) {
            @Override
            public void onNext(UserData userData) {
                if(userData != null){
                    userList = userData.getUser();
                }
                if(!userList.isEmpty()){
                    userView.updateRecyclerView(userList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshVenuesData(String keyword) {
        searchModel.searchVenues(new RefreshSubscriber<VenuesData>(context) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesList = venuesData.getGym();
                }
                if(!venuesList.isEmpty()){
                    venuesView.updateRecyclerView(venuesList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreCampaignData(RecyclerView recyclerView, String keyword,final int pageSize, int page) {
        searchModel.searchCampaign(new RequestMoreSubscriber<CampaignData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData != null){
                    campaignList = campaignData.getCampaign();
                }
                if(!campaignList.isEmpty()){
                    campaignView.updateRecyclerView(campaignList);
                }
                if(campaignList.size() < pageSize){
                    campaignView.showEndFooterView();
                }
            }
        },keyword,page);
    }

    @Override
    public void requestMoreCourseData(RecyclerView recyclerView, String keyword,final int pageSize, int page) {
        searchModel.searchCourse(new RequestMoreSubscriber<CourseData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null){
                    courseList = courseData.getCourse();
                }
                if(!courseList.isEmpty()){
                    courseView.updateRecyclerView(courseList);
                }
                if(courseList.size() < pageSize){
                    courseView.showEndFooterView();
                }
            }
        },keyword,page);
    }

    @Override
    public void requestMoreFoodData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchFood(new RequestMoreSubscriber<FoodData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(FoodData foodData) {
                if(foodData != null){
                    foodList = foodData.getFood();
                }
                if(!foodList.isEmpty()){
                    foodView.updateRecyclerView(foodList);
                }
                if(foodList.size() < pageSize){
                    foodView.showEndFooterView();
                }
            }
        },keyword,page);
    }

    @Override
    public void requestMoreUserData(RecyclerView recyclerView, String keyword,final int pageSize, int page) {
        searchModel.searchUser(new RequestMoreSubscriber<UserData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(UserData userData) {
                if(userData != null){
                    userList = userData.getUser();
                }
                if(!userList.isEmpty()){
                    userView.updateRecyclerView(userList);
                }
                if(userList.size() < pageSize){
                    userView.showEndFooterView();
                }
            }
        },keyword,page);
    }

    @Override
    public void requestMoreVenuesData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchVenues(new RequestMoreSubscriber<VenuesData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesList = venuesData.getGym();
                }
                if(!venuesList.isEmpty()){
                    venuesView.updateRecyclerView(venuesList);
                }
                if(venuesList.size() < pageSize){
                    venuesView.showEndFooterView();
                }
            }
        },keyword,page);
    }
}
