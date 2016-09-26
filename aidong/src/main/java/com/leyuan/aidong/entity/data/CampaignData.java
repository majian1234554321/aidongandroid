package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.CampaignBean;

import java.util.ArrayList;

/**
 * 活动数据实体
 * Created by song on 2016/8/18.
 */
public class CampaignData {
    private ArrayList<CampaignBean> campaign;

    public ArrayList<CampaignBean> getCampaign() {
        return campaign;
    }

    public void setCampaign(ArrayList<CampaignBean> campaign) {
        this.campaign = campaign;
    }

    @Override
    public String toString() {
        return "CampaignData{" +
                "campaign=" + campaign +
                '}';
    }
}
