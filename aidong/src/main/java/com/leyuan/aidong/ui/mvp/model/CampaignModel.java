package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CampaignDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;

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
    void getCampaigns(Subscriber<CampaignData> subscriber, int page,String list);

    /**
     * 获取活动详情
     * @param subscriber Subscriber
     * @param id 活动id
     */
    void getCampaignDetail(Subscriber<CampaignDetailData> subscriber, String id);

    /**
     * 购买活动
     * @param subscriber Subscriber
     * @param id 活动id
     * @param couponId 优惠券id
     * @param integral 使用积分数量
     * @param payType 支付类型
     * @param contactName 联系人
     * @param contactMobile 联系电话
     */
    void buyCampaign(Subscriber<PayOrderData> subscriber,String id,String couponId,float integral,
                     String payType,String contactName,String contactMobile);

}
