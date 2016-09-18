package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.UserBean;
import com.leyuan.support.entity.data.UserData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.SearchModel;
import com.leyuan.support.mvp.model.impl.SearchModelImpl;
import com.leyuan.support.mvp.presenter.SearchFragmentPresent;
import com.leyuan.support.mvp.view.SearchUserFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索用户
 * Created by song on 2016/9/18.
 */
public class SearchUserFragmentPresentImpl implements SearchFragmentPresent{
    private Context context;
    private SearchModel searchModel;
    private SearchUserFragmentView searchView;
    private List<UserBean> userList;

    public SearchUserFragmentPresentImpl(Context context, SearchUserFragmentView searchView) {
        this.context = context;
        this.searchView = searchView;
        searchModel = new SearchModelImpl();
        userList = new ArrayList<>();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String keyword) {
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
                    searchView.updateRecyclerView(userList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String keyword) {
        searchModel.searchUser(new RefreshSubscriber<UserData>(context) {
            @Override
            public void onNext(UserData userData) {
                if(userData != null){
                    userList = userData.getUser();
                }
                if(!userList.isEmpty()){
                    searchView.updateRecyclerView(userList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchUser(new RequestMoreSubscriber<UserData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(UserData userData) {
                if(userData != null){
                    userList = userData.getUser();
                }
                if(!userList.isEmpty()){
                    searchView.updateRecyclerView(userList);
                }
                if(userList.size() < pageSize){
                    searchView.showEndFooterView();
                }
            }
        },keyword,page);
    }
}
