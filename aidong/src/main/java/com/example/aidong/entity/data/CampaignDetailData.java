package com.example.aidong.entity.data;

import com.example.aidong.entity.CampaignDetailBean;

/**
 * 活动详情数据实体
 * Created by song on 2016/8/18.
 */
public class CampaignDetailData {
    private CampaignDetailBean campaign;

    public CampaignDetailBean getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignDetailBean campaign) {
        this.campaign = campaign;
    }

    @Override
    public String toString() {
        return "CampaignDetailData{" +
                "campaign=" + campaign +
                '}';
    }
}
