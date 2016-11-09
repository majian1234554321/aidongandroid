package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.data.DiscoverNewsData;
import com.leyuan.aidong.entity.data.DiscoverUserData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.DiscoverModel;
import com.leyuan.aidong.ui.mvp.model.impl.DiscoverModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.DiscoverPresent;
import com.leyuan.aidong.ui.mvp.view.DiscoverUserActivityView;
import com.leyuan.aidong.ui.mvp.view.SportNewsActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 * Created by song on 2016/8/29.
 */
public class DiscoverPresentImpl implements DiscoverPresent {
    private Context context;
    private DiscoverModel discoverModel;
    private DiscoverUserActivityView discoverUserActivityView;
    private SportNewsActivityView sportNewsActivityView;
    private List<UserBean> userBeanList = new ArrayList<>();
    private List<NewsBean> newsBeanList = new ArrayList<>();

    public DiscoverPresentImpl(Context context, DiscoverUserActivityView discoverUserActivityView) {
        this.context = context;
        this.discoverUserActivityView = discoverUserActivityView;
        if(discoverModel == null){
            discoverModel = new DiscoverModelImpl();
        }
    }

    public DiscoverPresentImpl(Context context, SportNewsActivityView sportNewsActivityView) {
        this.context = context;
        this.sportNewsActivityView = sportNewsActivityView;
        if(discoverModel == null){
            discoverModel = new DiscoverModelImpl();
        }
    }

    @Override
    public void commonLoadUserData(SwitcherLayout switcherLayout,double lat, double lng, String gender, String type) {
        discoverModel.getUsers(new CommonSubscriber<DiscoverUserData>(switcherLayout) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if(discoverUserData != null && discoverUserData.getUser() != null){
                    userBeanList = discoverUserData.getUser();
                }
                if(!userBeanList.isEmpty()){
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                }else{
                    discoverUserActivityView.showEmptyView();
                }
            }
        },lat,lng, Constant.FIRST_PAGE,gender,type);
    }

    @Override
    public void pullToRefreshUserData(double lat, double lng, String gender, String type) {
        discoverModel.getUsers(new RefreshSubscriber<DiscoverUserData>(context) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if(discoverUserData != null && discoverUserData.getUser() != null){
                    userBeanList = discoverUserData.getUser();
                }
                if(!userBeanList.isEmpty()){
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                }
            }
        },lat,lng, Constant.FIRST_PAGE,gender,type);
    }

    @Override
    public void requestMoreUserData(RecyclerView recyclerView, double lat, double lng, String gender, String type, final int pageSize, int page) {
        discoverModel.getUsers(new RequestMoreSubscriber<DiscoverUserData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if(discoverUserData != null && discoverUserData.getUser() != null){
                    userBeanList = discoverUserData.getUser();
                }
                if(!userBeanList.isEmpty()){
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                }
                //没有更多数据了显示到底提示
                if(userBeanList.size() < pageSize){
                    discoverUserActivityView.showEndFooterView();
                }
            }
        },lat,lng,page,gender,type);
    }

    @Override
    public void commonLoadNewsData(final SwitcherLayout switcherLayout) {
        discoverModel.getNews(new CommonSubscriber<DiscoverNewsData>(switcherLayout) {
            @Override
            public void onNext(DiscoverNewsData discoverNewsData) {
                if(discoverNewsData != null && discoverNewsData.getNews() != null){
                    newsBeanList = discoverNewsData.getNews();
                }
                if(!newsBeanList.isEmpty()){
                    sportNewsActivityView.updateRecyclerView(newsBeanList);
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshNewsData() {
        discoverModel.getNews(new RefreshSubscriber<DiscoverNewsData>(context) {
            @Override
            public void onNext(DiscoverNewsData discoverNewsData) {
                if(discoverNewsData != null && discoverNewsData.getNews() != null){
                    newsBeanList = discoverNewsData.getNews();
                }
                if(!newsBeanList.isEmpty()){
                    sportNewsActivityView.updateRecyclerView(newsBeanList);
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreNewsData(RecyclerView recyclerView, final int pageSize,int page) {
        discoverModel.getNews(new RequestMoreSubscriber<DiscoverNewsData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(DiscoverNewsData discoverNewsData) {
                if(discoverNewsData != null && discoverNewsData.getNews() != null){
                    newsBeanList = discoverNewsData.getNews();
                }
                if(!newsBeanList.isEmpty()){
                    sportNewsActivityView.updateRecyclerView(newsBeanList);
                }
                //没有更多数据了显示到底提示
                if(newsBeanList.size() < pageSize){
                    sportNewsActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
