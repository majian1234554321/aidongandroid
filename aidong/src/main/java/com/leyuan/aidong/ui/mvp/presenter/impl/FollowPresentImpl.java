package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.data.FollowData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.FollowModel;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.FollowPresent;
import com.leyuan.aidong.ui.mvp.view.FollowFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注
 * Created by song on 2016/8/19.
 */
public class FollowPresentImpl implements FollowPresent {
    private Context context;
    private FollowModel followModel;
    private FollowFragmentView followFragmentView;
    private List<UserBean> userBeanList = new ArrayList<>();

    public FollowPresentImpl(Context context, FollowFragmentView followFragmentView) {
        this.context = context;
        this.followFragmentView = followFragmentView;
        followModel = new FollowModelImpl();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String type) {
        followModel.getFollow(new CommonSubscriber<FollowData>(switcherLayout) {
            @Override
            public void onNext(FollowData followData) {
                if(followData != null && !followData.getFollow().isEmpty()){
                    userBeanList = followData.getFollow();
                }
                if(!userBeanList.isEmpty()){
                    followFragmentView.updateRecyclerView(userBeanList);
                    switcherLayout.showContentLayout();
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },type,Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String type) {
        followModel.getFollow(new RefreshSubscriber<FollowData>(context) {
            @Override
            public void onNext(FollowData followData) {
                if(followData != null && !followData.getFollow().isEmpty()){
                    followFragmentView.updateRecyclerView(followData.getFollow());
                }
            }
        },type,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, String type, int page) {
        followModel.getFollow(new RequestMoreSubscriber<FollowData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(FollowData followData) {
                if(followData != null){
                    userBeanList = followData.getFollow();
                }
                if(!userBeanList.isEmpty()){
                    followFragmentView.updateRecyclerView(userBeanList);
                }
                //没有更多数据了显示到底提示
                if( userBeanList.size() < pageSize){
                    followFragmentView.showEndFooterView();
                }
            }
        },type,page);
    }

    @Override
    public void addFollow(int id) {

    }

    @Override
    public void cancelFollow(int id) {

    }
}