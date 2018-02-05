package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.data.FollowData;
import com.leyuan.aidong.entity.data.UserData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.SearchModelImpl;
import com.leyuan.aidong.ui.mvp.view.FollowFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/2/5.
 */
public class SelectedUserPrensenterImpl {
    private Context context;
    private FollowModelImpl followModel;
    private FollowFragmentView followFragment;

    private List<UserBean> userBeanList = new ArrayList<>();

    public SelectedUserPrensenterImpl(Context context, FollowFragmentView followFragment) {
        this.context = context;
        this.followFragment = followFragment;
        followModel = new FollowModelImpl();
        searchModel = new SearchModelImpl();
    }


    public void commonLoadData(final SwitcherLayout switcherLayout, final String type) {
        followModel.getFollow(new CommonSubscriber<FollowData>(context, switcherLayout) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null && !followData.getFollow().isEmpty()) {
                    userBeanList = followData.getFollow();
                }
                if (userBeanList != null && !userBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    followFragment.onRefreshData(userBeanList);
                } else {
                    followFragment.showEmptyView();
                }
            }
        }, type, Constant.PAGE_FIRST);
    }

    public void pullToRefreshData(final String type) {
        followModel.getFollow(new RefreshSubscriber<FollowData>(context) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null && !followData.getFollow().isEmpty()) {
                    followFragment.onRefreshData(followData.getFollow());
                }
            }
        }, type, Constant.PAGE_FIRST);
    }

    public void requestMoreData(RecyclerView recyclerView, final int pageSize, String type, int page) {
        followModel.getFollow(new RequestMoreSubscriber<FollowData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null) {
                    userBeanList = followData.getFollow();
                }
                if (!userBeanList.isEmpty()) {
                    followFragment.onLoadMoreData(userBeanList);
                }
                //没有更多数据了显示到底提示
                if (userBeanList.size() < pageSize) {
                    followFragment.showEndFooterView();

                }
            }
        }, type, page);
    }

    private SearchModelImpl searchModel;


    public void commonSearcjUser(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchUserIt(new CommonSubscriber<UserData>(context, switcherLayout) {
            @Override
            public void onNext(UserData userData) {
                if (userData != null) {
                    userBeanList = userData.getUser();
                }
                if (userBeanList.isEmpty()) {
                    followFragment.showEmptyView();
                } else {
                    switcherLayout.showContentLayout();
                    followFragment.onRefreshData(userBeanList);
                }
            }
        }, keyword, Constant.PAGE_FIRST);
    }


    public void pullToRefreshUserData(String keyword) {
        searchModel.searchUser(new RefreshSubscriber<UserData>(context) {
            @Override
            public void onNext(UserData userData) {
                if (userData != null) {
                    userBeanList = userData.getUser();
                }
                if (!userBeanList.isEmpty()) {
                    followFragment.onRefreshData(userBeanList);
                }
            }
        }, keyword, Constant.PAGE_FIRST);
    }

    public void requestMoreUserData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchUser(new RequestMoreSubscriber<UserData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(UserData userData) {
                if (userData != null) {
                    userBeanList = userData.getUser();
                }
                if (!userBeanList.isEmpty()) {
                    followFragment.onLoadMoreData(userBeanList);
                }
                if (userBeanList.size() < pageSize) {
                    followFragment.showEndFooterView();
                }
            }
        }, keyword, page);
    }

}
