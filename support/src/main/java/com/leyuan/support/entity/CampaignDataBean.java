package com.leyuan.support.entity;

import java.util.ArrayList;

/**
 * 活动数据实体
 * Created by song on 2016/8/18.
 */
public class CampaignDataBean {
    private ArrayList<CampaignBean> campaign;

    public ArrayList<CampaignBean> getCampaign() {
        return campaign;
    }

    public void setCampaign(ArrayList<CampaignBean> campaign) {
        this.campaign = campaign;
    }

    @Override
    public String toString() {
        return "CampaignDataBean{" +
                "campaign=" + campaign +
                '}';
    }
}
