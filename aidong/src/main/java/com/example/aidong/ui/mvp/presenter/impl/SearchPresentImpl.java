package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CampaignBean;
import com.example.aidong .entity.CourseBean;
import com.example.aidong .entity.EquipmentBean;
import com.example.aidong .entity.NurtureBean;
import com.example.aidong .entity.UserBean;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .entity.data.CampaignData;
import com.example.aidong .entity.data.CourseData;
import com.example.aidong .entity.data.EquipmentData;
import com.example.aidong .entity.data.SearchNurtureData;
import com.example.aidong .entity.data.UserData;
import com.example.aidong .entity.data.VenuesData;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .http.subscriber.RefreshSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .ui.mvp.model.FollowModel;
import com.example.aidong .ui.mvp.model.SearchModel;
import com.example.aidong .ui.mvp.model.impl.FollowModelImpl;
import com.example.aidong .ui.mvp.model.impl.SearchModelImpl;
import com.example.aidong .ui.mvp.presenter.SearchPresent;
import com.example.aidong .ui.mvp.view.SearchActivityView;
import com.example.aidong .ui.mvp.view.SearchCampaignFragmentView;
import com.example.aidong .ui.mvp.view.SearchCourseFragmentView;
import com.example.aidong .ui.mvp.view.SearchEquipmentFragmentView;
import com.example.aidong .ui.mvp.view.SearchNurtureFragmentView;
import com.example.aidong .ui.mvp.view.SearchUserFragmentView;
import com.example.aidong .ui.mvp.view.SearchVenuesFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * 搜索
 * Created by song on 2016/9/21.
 */
//todo 优化
public class SearchPresentImpl implements SearchPresent{
    private Context context;
    private SearchModel searchModel;
    private FollowModel followModel;

    private List<CampaignBean> campaignList;

    private List<CourseBean> courseList = new ArrayList<>();
    private List<NurtureBean> nurtureList = new ArrayList<>();
    private List<EquipmentBean> equipmentList = new ArrayList<>();
    private List<UserBean> userList = new ArrayList<>();
    private List<VenuesBean> venuesList = new ArrayList<>();

    private SearchActivityView searchActivityView;      //搜索View层对象
    private SearchCampaignFragmentView campaignView;    //搜索活动View层对象
    private SearchCourseFragmentView courseView;        //搜索课程View层对象
    private SearchNurtureFragmentView nurtureView;      //搜索营养品View层对象
    private SearchEquipmentFragmentView equipmentView;  //搜索装备View层对象
    private SearchUserFragmentView userView;            //搜索用户View层对象
    private SearchVenuesFragmentView venuesView;        //搜索场馆View层对象

    public SearchPresentImpl(Context context, SearchActivityView view, Realm realm) {
        this.context = context;
        this.searchActivityView = view;
        if(searchModel == null){
            searchModel = new SearchModelImpl(realm);
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

    public SearchPresentImpl(Context context, SearchEquipmentFragmentView view) {
        this.context = context;
        this.equipmentView = view;
        campaignList = new ArrayList<>();
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    public SearchPresentImpl(Context context, SearchCourseFragmentView view) {
        this.context = context;
        this.courseView = view;
        nurtureList = new ArrayList<>();
        if(searchModel == null){
            searchModel = new SearchModelImpl();
        }
    }

    public SearchPresentImpl(Context context, SearchNurtureFragmentView view) {
        this.context = context;
        this.nurtureView = view;
        equipmentList = new ArrayList<>();
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
    public void deleteAllHistory() {
        searchModel.deleteSearchHistory();
    }

    @Override
    public void commonLoadData(SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchData(new CommonSubscriber<Object>(context,switcherLayout) {
            @Override
            public void onNext(Object o) {
                searchActivityView.setSearchResult(o);
            }
        },keyword);

    }

    @Override
    public void commonLoadCampaignData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchCampaign(new CommonSubscriber<CampaignData>(context,switcherLayout) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData != null){
                    campaignList = campaignData.getCampaign();
                }
                if(campaignList.isEmpty()){
                    campaignView.showEmptyView();
                }else {
                    switcherLayout.showContentLayout();
                    campaignView.onRecyclerViewRefresh(campaignList);
                }
            }
        },keyword, Constant.PAGE_FIRST);
    }

    @Override
    public void commonLoadEquipmentData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchEquipment(new CommonSubscriber<EquipmentData>(context,switcherLayout) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if (equipmentData != null) {
                    equipmentList = equipmentData.getEquipment();
                }
                if(equipmentList.isEmpty()){
                    equipmentView.showEmptyView();
                }else {
                    switcherLayout.showContentLayout();
                    equipmentView.onRecyclerViewRefresh(equipmentList);
                }
            }
        },keyword, Constant.PAGE_FIRST);
    }

    @Override
    public void commonLoadCourseData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchCourse(new CommonSubscriber<CourseData>(context,switcherLayout) {
            @Override
            public void onNext(CourseData courseData) {
                if(courseData != null){
                    courseList = courseData.getCourse();
                }
                if(courseList.isEmpty()){
                    courseView.showEmptyView();
                }else {
                    switcherLayout.showContentLayout();
                    courseView.onRecyclerViewRefresh(courseList);
                }
            }
        },keyword, Constant.PAGE_FIRST);
    }

    @Override
    public void commonLoadNurtureData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchNurture(new CommonSubscriber<SearchNurtureData>(context,switcherLayout) {
            @Override
            public void onNext(SearchNurtureData searchNurtureData) {
                if(searchNurtureData != null){
                    nurtureList = searchNurtureData.getNutrition();
                }

                if(nurtureList.isEmpty()){
                    nurtureView.showEmptyView();
                }else {
                    switcherLayout.showContentLayout();
                    nurtureView.onRecyclerViewRefresh(nurtureList);
                }
            }
        },keyword, Constant.PAGE_FIRST);
    }

    @Override
    public void commonUserData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchUser(new CommonSubscriber<UserData>(context,switcherLayout) {
            @Override
            public void onNext(UserData userData) {
                if(userData != null){
                    userList = userData.getUser();
                }
                if(userList.isEmpty()){
                    userView.showEmptyView();
                }else {
                    switcherLayout.showContentLayout();
                    userView.onRecyclerViewRefresh(userList);
                }
            }
        },keyword, Constant.PAGE_FIRST);
    }

    public void commonSearcjUser(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchUserIt(new CommonSubscriber<UserData>(context,switcherLayout) {
            @Override
            public void onNext(UserData userData) {
                if(userData != null){
                    userList = userData.getUser();
                }
                if(userList.isEmpty()){
                    userView.showEmptyView();
                }else {
                    switcherLayout.showContentLayout();
                    userView.onRecyclerViewRefresh(userList);
                }
            }
        },keyword, Constant.PAGE_FIRST);
    }

    @Override
    public void commonLoadVenuesData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchVenues(new CommonSubscriber<VenuesData>(context,switcherLayout) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesList = venuesData.getGym();
                }
                if(venuesList.isEmpty()){
                    venuesView.showEmptyView();
                }else {
                    switcherLayout.showContentLayout();
                    venuesView.onRecyclerViewRefresh(venuesList);
                }
            }
        },keyword, Constant.PAGE_FIRST);
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
                    campaignView.onRecyclerViewRefresh(campaignList);
                }
            }
        },keyword,Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshEquipmentData(String keyword) {
        searchModel.searchEquipment(new RefreshSubscriber<EquipmentData>(context) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if(equipmentData != null){
                    equipmentList = equipmentData.getEquipment();
                }
                if(!equipmentList.isEmpty()){
                    equipmentView.onRecyclerViewRefresh(equipmentList);
                }
            }
        },keyword,Constant.PAGE_FIRST);
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
                    courseView.onRecyclerViewRefresh(courseList);
                }
            }
        },keyword,Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshNurtureData(String keyword) {
        searchModel.searchNurture(new RefreshSubscriber<SearchNurtureData>(context) {
            @Override
            public void onNext(SearchNurtureData searchNurtureData) {
                if (searchNurtureData != null) {
                    nurtureList = searchNurtureData.getNutrition();
                }
                if (!nurtureList.isEmpty()) {
                    nurtureView.onRecyclerViewRefresh(nurtureList);
                }
            }
        },keyword,Constant.PAGE_FIRST);
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
                    userView.onRecyclerViewRefresh(userList);
                }
            }
        },keyword,Constant.PAGE_FIRST);
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
                    venuesView.onRecyclerViewRefresh(venuesList);
                }
            }
        },keyword,Constant.PAGE_FIRST);
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
                    campaignView.onRecyclerViewLoadMore(campaignList);
                }
                if(campaignList.size() < pageSize){
                    campaignView.showEndFooterView();
                }
            }
        },keyword,page);
    }

    @Override
    public void requestMoreEquipmentData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchEquipment(new RequestMoreSubscriber<EquipmentData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(EquipmentData equipmentData) {
                if(equipmentData != null){
                    equipmentList = equipmentData.getEquipment();
                }
                if(!equipmentList.isEmpty()){
                    equipmentView.onRecyclerViewLoadMore(equipmentList);
                }
                if(nurtureList.size() < pageSize){
                    equipmentView.showEndFooterView();
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
                    courseView.onRecyclerViewLoadMore(courseList);
                }
                if(courseList.size() < pageSize){
                    courseView.showEndFooterView();
                }
            }
        },keyword,page);
    }

    @Override
    public void requestMoreNurtureData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchNurture(new RequestMoreSubscriber<SearchNurtureData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(SearchNurtureData searchNurtureData) {
                if(searchNurtureData != null){
                    nurtureList = searchNurtureData.getNutrition();
                }
                if(!nurtureList.isEmpty()){
                    nurtureView.onRecyclerViewLoadMore(nurtureList);
                }
                if(equipmentList.size() < pageSize){
                    nurtureView.showEndFooterView();
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
                    userView.onRecyclerViewLoadMore(userList);
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
                    venuesView.onRecyclerViewLoadMore(venuesList);
                }
                if(venuesList.size() < pageSize){
                    venuesView.showEndFooterView();
                }
            }
        },keyword,page);
    }

    @Override
    public void addFollow(String id) {
        if(followModel == null){
            followModel = new FollowModelImpl();
        }
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean != null){
                    userView.addFollowResult(baseBean);
                }
            }
        },id,Constant.USER);
    }

    @Override
    public void cancelFollow(String id) {
        if(followModel == null){
            followModel = new FollowModelImpl();
        }
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean != null){
                    userView.cancelFollowResult(baseBean);
                }
            }
        },id,Constant.USER);
    }
}
