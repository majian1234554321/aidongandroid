package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CouponBean;

import java.util.List;

/**
 * 预约场馆
 * Created by song on 2017/3/9.
 */
public interface AppointCampaignActivityView {

    void setCampaignCouponResult(List<CouponBean> couponBeanList);


    void OnBuyError();
}
