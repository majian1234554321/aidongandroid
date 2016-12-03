package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.entity.PayOrderBean;

/**
 * 活动详情
 * Created by song on 2016/8/18.
 */
public interface CampaignDetailActivityView {
    /**
     * 获取活动详情
     * @param campaignDetailBean CampaignDetailBean
     */
    void setCampaignDetail(CampaignDetailBean campaignDetailBean);

    /**
     * 购买活动回调
     * @param payOrderBean PayOrderBean
     */
    void onBuyCampaign(PayOrderBean payOrderBean);

    /**
     * 分享此活动
     */
    void shareCampaign();

    /**
     * 报名参加此活动
     */
    void applyCampaign();



}
