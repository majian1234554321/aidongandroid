package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.UserBean;
import com.leyuan.support.entity.data.FollowerData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.FollowModel;
import com.leyuan.support.mvp.model.impl.FollowModelImpl;
import com.leyuan.support.mvp.presenter.FansFragmentPresent;
import com.leyuan.support.mvp.view.FansFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝
 * Created by song on 2016/8/19.
 */
public class FansFragmentPresentImpl implements FansFragmentPresent{
    private Context context;
    private FollowModel followModel;
    private FansFragmentView fansFragmentView;
    private List<UserBean> fansList = new ArrayList<>();

    public FansFragmentPresentImpl(FansFragmentView fansFragmentView, Context context) {
        this.context = context;
        this.fansFragmentView = fansFragmentView;
        followModel = new FollowModelImpl();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        followModel.getFollows(new CommonSubscriber<FollowerData>(switcherLayout) {
            @Override
            public void onNext(FollowerData followerData) {
                if(followerData != null){
                    fansList = followerData.getFollower();
                }
                if(!fansList.isEmpty()){
                    fansFragmentView.updateRecyclerView(fansList);
                    switcherLayout.showContentLayout();
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData() {
        followModel.getFollows(new RefreshSubscriber<FollowerData>(context) {
            @Override
            public void onNext(FollowerData followerData) {
                if(followerData != null){
                    fansList = followerData.getFollower();
                }

                if(!fansList.isEmpty()) {
                    fansFragmentView.updateRecyclerView(fansList);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        followModel.getFollows(new RequestMoreSubscriber<FollowerData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(FollowerData followerData) {
                if(followerData != null){
                    fansList = followerData.getFollower();
                }
                if(!fansList.isEmpty()) {
                    fansFragmentView.updateRecyclerView(fansList);
                }
                if(fansList.size() < pageSize){
                    fansFragmentView.showEndFooterView();
                }
            }
        },page);
    }

    @Override
    public void addFollow(int id) {

    }

    @Override
    public void cancelFollow(int id) {

    }
}
