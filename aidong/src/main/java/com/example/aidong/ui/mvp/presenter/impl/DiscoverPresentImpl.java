package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.entity.UserBean;
import com.example.aidong.entity.data.DiscoverUserData;
import com.example.aidong.http.subscriber.CommonSubscriber;
import com.example.aidong.http.subscriber.RefreshSubscriber;
import com.example.aidong.http.subscriber.RequestMoreSubscriber;
import com.example.aidong.ui.mvp.model.DiscoverModel;
import com.example.aidong.ui.mvp.model.impl.DiscoverModelImpl;
import com.example.aidong.ui.mvp.presenter.DiscoverPresent;
import com.example.aidong.ui.mvp.view.DiscoverUserActivityView;
import com.example.aidong.utils.Constant;
import com.example.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现 - 人
 * Created by song on 2016/8/29.
 */
public class DiscoverPresentImpl implements DiscoverPresent {
    private Context context;
    private DiscoverUserActivityView discoverUserActivityView;
    private DiscoverModel discoverModel;
    private List<UserBean> userBeanList = new ArrayList<>();

    public DiscoverPresentImpl(Context context, DiscoverUserActivityView discoverUserActivityView) {
        this.context = context;
        this.discoverUserActivityView = discoverUserActivityView;
        discoverModel = new DiscoverModelImpl();
    }

    @Override
    public void commonLoadData(SwitcherLayout switcherLayout,double lat, double lng, String gender, String type) {
        discoverModel.getUsers(new CommonSubscriber<DiscoverUserData>(switcherLayout) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if(discoverUserData != null){
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
    public void pullToRefreshData(double lat, double lng, String gender, String type) {
        discoverModel.getUsers(new RefreshSubscriber<DiscoverUserData>(context) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if(discoverUserData != null){
                    userBeanList = discoverUserData.getUser();
                }
                if(!userBeanList.isEmpty()){
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                }
            }
        },lat,lng, Constant.FIRST_PAGE,gender,type);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, double lat, double lng, String gender, String type, final int pageSize, int page) {
        discoverModel.getUsers(new RequestMoreSubscriber<DiscoverUserData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if(discoverUserData != null){
                    userBeanList = discoverUserData.getUser();
                }
                if(userBeanList != null && !userBeanList.isEmpty()){
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                }
                //没有更多数据了显示到底提示
                if(userBeanList != null && userBeanList.size() < pageSize){
                    discoverUserActivityView.showEndFooterView();
                }
            }
        },lat,lng,page,gender,type);
    }
}
