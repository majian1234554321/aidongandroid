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

    /**
     * 显示加载中布局
     */
    void showLoadingView();

    /**
     * 显示正常加载到的内容
     */
    void showContent();

    /**
     * 显示网络错误布局
     */
    void showNetErrorView();

    /**
     * 活动无内容,比如无此活动布局
     */
    void showNoContentView();

}
