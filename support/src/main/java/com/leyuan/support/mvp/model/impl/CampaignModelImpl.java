package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.entity.CampaignDetailBean;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.CampaignService;
import com.leyuan.support.mvp.model.CampaignModel;

import java.util.List;

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
    public void getCampaigns(Subscriber<List<CampaignBean>> subscriber, int page) {
        campaignService.getCampaigns(page)
                .compose(RxHelper.<List<CampaignBean>>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCampaignDetail(Subscriber<CampaignDetailBean> subscriber, int id) {
        campaignService.getCampaignDetail(id)
                .compose(RxHelper.<CampaignDetailBean>transform())
                .subscribe(subscriber);
    }


}
