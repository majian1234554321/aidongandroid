package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CampaignDetailData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CampaignService;
import com.leyuan.aidong.ui.mvp.model.CampaignModel;

import rx.Subscriber;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public class CampaignModelImpl implements CampaignModel{
    private CampaignService campaignService;

    public CampaignModelImpl() {
        campaignService = RetrofitHelper.createApi(CampaignService.class);
    }


    @Override
    public void getCampaigns(Subscriber<CampaignData> subscriber, int page) {
        campaignService.getCampaigns(page)
                .compose(RxHelper.<CampaignData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCampaignDetail(Subscriber<CampaignDetailData> subscriber, String id) {
        campaignService.getCampaignDetail(id)
                .compose(RxHelper.<CampaignDetailData>transform())
                .subscribe(subscriber);
    }
}