package com.example.aidong.ui.mvp.model;

import com.example.aidong.entity.data.CampaignData;
import com.example.aidong.entity.data.CampaignDetailData;

import rx.Subscriber;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignModel {
    /**
     * 获取活动列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getCampaigns(Subscriber<CampaignData> subscriber, int page);

    /**
     * 获取活动详情
     * @param subscriber Subscriber
     * @param id 活动id
     */
    void getCampaignDetail(Subscriber<CampaignDetailData> subscriber, String id);

}
