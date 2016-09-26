package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CouponData;

import rx.Subscriber;

/**
 * 优惠劵
 * Created by song on 2016/9/1.
 */
public interface CouponModel {

    /**
     * 获取优惠劵列表
     * @param subscriber Subscriber
     * @param type valid: 有效的 used: 已使用的 expired:已过期的
     * @param page 页码
     */
    void getCoupons(Subscriber<CouponData> subscriber, String type, int page);


    /**
     * 领取优惠劵
     * @param subscriber Subscriber
     * @param id 优惠劵id
     */
    void obtainCoupon(Subscriber<BaseBean> subscriber, String id);
}
