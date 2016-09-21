package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.entity.data.CampaignData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.SearchModel;
import com.leyuan.support.mvp.model.impl.SearchModelImpl;
import com.leyuan.support.mvp.presenter.SearchPresent;
import com.leyuan.support.mvp.view.SearchCampaignFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索活动
 * Created by song on 2016/9/18.
 */
public class SearchCampaignFragmentPresentImpl implements SearchPresent {
    private Context context;
    private SearchModel searchModel;
    private SearchCampaignFragmentView searchView;
    private List<CampaignBean> campaignList;

    public SearchCampaignFragmentPresentImpl(Context context, SearchCampaignFragmentView searchView) {
        this.context = context;
        this.searchView = searchView;
        searchModel = new SearchModelImpl();
        campaignList = new ArrayList<>();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchCampaign(new CommonSubscriber<CampaignData>(switcherLayout) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData != null){
                    campaignList = campaignData.getCampaign();
                }
                if(campaignList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else {
                    switcherLayout.showContentLayout();
                    searchView.updateRecyclerView(campaignList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String keyword) {
        searchModel.searchCampaign(new RefreshSubscriber<CampaignData>(context) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData != null){
                    campaignList = campaignData.getCampaign();
                }
                if(!campaignList.isEmpty()){
                    searchView.updateRecyclerView(campaignList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchCampaign(new RequestMoreSubscriber<CampaignData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData != null){
                    campaignList = campaignData.getCampaign();
                }
                if(!campaignList.isEmpty()){
                    searchView.updateRecyclerView(campaignList);
                }
                if(campaignList.size() < pageSize){
                    searchView.showEndFooterView();
                }
            }
        },keyword,page);
    }
}
