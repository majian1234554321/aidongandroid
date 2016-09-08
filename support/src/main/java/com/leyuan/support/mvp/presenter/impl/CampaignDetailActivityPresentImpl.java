package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.support.entity.data.CampaignDetailData;
import com.leyuan.support.mvp.model.CampaignModel;
import com.leyuan.support.mvp.model.impl.CampaignModelImpl;
import com.leyuan.support.mvp.presenter.CampaignDetailActivityPresent;
import com.leyuan.support.mvp.view.CampaignDetailActivityView;

import rx.Subscriber;

/**
 * 活动详情
 * Created by song on 2016/8/17.
 */
public class CampaignDetailActivityPresentImpl implements CampaignDetailActivityPresent{

    private Context context;
    private CampaignDetailActivityView campaignDetailView;
    private CampaignModel campaignModel;

    public CampaignDetailActivityPresentImpl(Context context, CampaignDetailActivityView campaignDetailActivityView) {
        this.context = context;
        this.campaignDetailView = campaignDetailActivityView;
        campaignModel = new CampaignModelImpl();
    }

    @Override
    public void getCampaignDetail(String id) {
        campaignModel.getCampaignDetail(new Subscriber<CampaignDetailData>() {
            @Override
            public void onStart() {
                campaignDetailView.showLoadingView();
            }

            @Override
            public void onNext(CampaignDetailData campaignDetailData) {
                if(campaignDetailData.getCampaign() != null){
                    campaignDetailView.getCampaignDetail(campaignDetailData.getCampaign());
                }else{
                    campaignDetailView.showNoContentView();
                }
            }

            @Override
            public void onCompleted() {
                campaignDetailView.showContent();
            }

            @Override
            public void onError(Throwable e) {
                campaignDetailView.showNetErrorView();
            }
        }, id);
    }
}
