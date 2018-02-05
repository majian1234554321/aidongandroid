package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.AppointmentDetailData;
import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CampaignDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;
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
    public void getCampaigns(Subscriber<CampaignData> subscriber, int page,String list) {
        campaignService.getCampaigns(page,list)
                .compose(RxHelper.<CampaignData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCampaignDetail(Subscriber<CampaignDetailData> subscriber, String id) {
        campaignService.getCampaignDetail(id)
                .compose(RxHelper.<CampaignDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void buyCampaign(Subscriber<PayOrderData> subscriber, String id, String couponId,
                            float integral, String payType, String contactName, String contactMobile, String amount) {
        campaignService.buyCampaign(id,couponId,integral,payType,contactName,contactMobile,amount)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCampaignAppointDetail(Subscriber<AppointmentDetailData> subscriber, String id) {
        campaignService.getCampaignAppointDetail(id)
                .compose(RxHelper.<AppointmentDetailData>transform())
                .subscribe(subscriber);
    }
}
