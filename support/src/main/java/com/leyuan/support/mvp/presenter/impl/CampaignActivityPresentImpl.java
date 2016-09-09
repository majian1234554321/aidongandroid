package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.entity.data.CampaignData;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.CampaignModel;
import com.leyuan.support.mvp.model.impl.CampaignModelImpl;
import com.leyuan.support.mvp.presenter.CampaignPresent;
import com.leyuan.support.mvp.view.CampaignView;
import com.leyuan.support.util.Constant;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public class CampaignActivityPresentImpl implements CampaignPresent {
    private Context context;
    private CampaignView campaignActivityView;
    private CampaignModel campaignModel;
    private List<CampaignBean> campaignBeanList;

    public CampaignActivityPresentImpl(Context context, CampaignView campaignActivityView) {
        this.context = context;
        this.campaignActivityView = campaignActivityView;
        campaignModel = new CampaignModelImpl();
        campaignBeanList = new ArrayList<>();
    }

    @Override
    public void normalLoadingData() {
        campaignModel.getCampaigns(new Subscriber<CampaignData>() {

            @Override
            public void onStart() {
                campaignActivityView.showLoadingView();
            }

            @Override
            public void onError(Throwable e) {
                campaignActivityView.showErrorView();
            }

            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData.getCampaign() != null && !campaignData.getCampaign().isEmpty()){
                    campaignActivityView.updateRecyclerView(campaignData.getCampaign());
                }else{
                    campaignActivityView.showEmptyView();
                }
            }

            @Override
            public void onCompleted() {
                campaignActivityView.showContentView();
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        campaignModel.getCampaigns(new RefreshSubscriber<CampaignData>(context,recyclerView) {
            @Override
            public void onNext(CampaignData campaignBean) {
                if(campaignBean != null && !campaignBean.getCampaign().isEmpty()){
                    campaignActivityView.updateRecyclerView(campaignBean.getCampaign());
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        campaignModel.getCampaigns(new RequestMoreSubscriber<CampaignData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(CampaignData campaignDataBean) {
                if(campaignDataBean != null){
                    campaignBeanList = campaignDataBean.getCampaign();
                }else{
                    campaignActivityView.showEndFooterView();
                }

                if(!campaignBeanList.isEmpty()){
                    campaignActivityView.updateRecyclerView(campaignBeanList);
                }

                //没有更多数据了显示到底提示
                if( campaignBeanList.size() < pageSize){
                    campaignActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
