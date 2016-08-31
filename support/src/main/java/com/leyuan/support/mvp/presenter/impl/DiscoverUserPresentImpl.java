package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.UserBean;
import com.leyuan.support.entity.data.DiscoverUserData;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.DiscoverModel;
import com.leyuan.support.mvp.model.impl.DiscoverModelImpl;
import com.leyuan.support.mvp.presenter.DiscoverUserPresent;
import com.leyuan.support.mvp.view.DiscoverUserActivityView;
import com.leyuan.support.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现 - 人
 * Created by song on 2016/8/29.
 */
public class DiscoverUserPresentImpl implements DiscoverUserPresent{
    private Context context;
    private DiscoverUserActivityView discoverUserActivityView;
    private DiscoverModel discoverModel;
    private List<UserBean> userBeanList;

    public DiscoverUserPresentImpl(Context context, DiscoverUserActivityView discoverUserActivityView) {
        this.context = context;
        this.discoverUserActivityView = discoverUserActivityView;
        discoverModel = new DiscoverModelImpl();
        userBeanList = new ArrayList<>();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView, double lat, double lng, String gender, String type) {
        discoverModel.getUsers(new RefreshSubscriber<DiscoverUserData>(context,recyclerView) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if(discoverUserData != null){
                    userBeanList = discoverUserData.getUser();
                }

                if(userBeanList != null && !userBeanList.isEmpty()){
                    discoverUserActivityView.hideEmptyView();
                    discoverUserActivityView.showRecyclerView();
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                }else {
                    discoverUserActivityView.showEmptyView();
                    discoverUserActivityView.hideRecyclerView();
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
