package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.entity.data.VenuesData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.SearchModel;
import com.leyuan.support.mvp.model.impl.SearchModelImpl;
import com.leyuan.support.mvp.presenter.SearchFragmentPresent;
import com.leyuan.support.mvp.view.SearchVenuesFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索场馆
 * Created by song on 2016/9/18.
 */
public class SearchVenuesFragmentPresentImpl implements SearchFragmentPresent{
    private Context context;
    private SearchModel searchModel;
    private SearchVenuesFragmentView searchView;
    private List<VenuesBean> venuesList;

    public SearchVenuesFragmentPresentImpl(Context context, SearchVenuesFragmentView searchView) {
        this.context = context;
        this.searchView = searchView;
        searchModel = new SearchModelImpl();
        venuesList = new ArrayList<>();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String keyword) {
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
                    searchView.updateRecyclerView(venuesList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String keyword) {
        searchModel.searchVenues(new RefreshSubscriber<VenuesData>(context) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesList = venuesData.getGym();
                }
                if(!venuesList.isEmpty()){
                    searchView.updateRecyclerView(venuesList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchVenues(new RequestMoreSubscriber<VenuesData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(VenuesData venuesData) {
                if(venuesData != null){
                    venuesList = venuesData.getGym();
                }
                if(!venuesList.isEmpty()){
                    searchView.updateRecyclerView(venuesList);
                }
                if(venuesList.size() < pageSize){
                    searchView.showEndFooterView();
                }
            }
        },keyword,page);
    }
}
