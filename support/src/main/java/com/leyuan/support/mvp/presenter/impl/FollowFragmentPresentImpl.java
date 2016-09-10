package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.UserBean;
import com.leyuan.support.entity.data.FollowingData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.FollowModel;
import com.leyuan.support.mvp.model.impl.FollowModelImpl;
import com.leyuan.support.mvp.presenter.FollowFragmentPresent;
import com.leyuan.support.mvp.view.FollowFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注
 * Created by song on 2016/8/19.
 */
public class FollowFragmentPresentImpl implements FollowFragmentPresent {
    private Context context;
    private FollowModel followModel;
    private FollowFragmentView followFragmentView;
    private List<UserBean> userBeanList = new ArrayList<>();

    public FollowFragmentPresentImpl(Context context, FollowFragmentView followFragmentView) {
        this.context = context;
        this.followFragmentView = followFragmentView;
        followModel = new FollowModelImpl();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        followModel.getFollowings(new CommonSubscriber<FollowingData>(switcherLayout) {
            @Override
            public void onNext(FollowingData followingData) {
                if(followingData != null){
                    userBeanList = followingData.getFollowing();
                }

                if(!userBeanList.isEmpty()){
                    followFragmentView.updateRecyclerView(userBeanList);
                    switcherLayout.showContentLayout();
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData() {
        followModel.getFollowings(new RefreshSubscriber<FollowingData>(context) {
            @Override
            public void onNext(FollowingData followingData) {
                if(followingData != null){
                    userBeanList = followingData.getFollowing();
                }

                if(!userBeanList.isEmpty()){
                    followFragmentView.updateRecyclerView(userBeanList);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        followModel.getFollowings(new RequestMoreSubscriber<FollowingData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(FollowingData followingData) {
                if(followingData != null){
                    userBeanList = followingData.getFollowing();
                }

                if(!userBeanList.isEmpty()){
                    followFragmentView.updateRecyclerView(userBeanList);
                }

                //没有更多数据了显示到底提示
                if( userBeanList.size() < pageSize){
                    followFragmentView.showEndFooterView();
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
