package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.user.CouponDataSingle;

import rx.Subscriber;

/**
 * 优惠劵
 * Created by song on 2016/9/1.
 */
public interface CouponModel {

    /**
     * 获取优惠劵列表
     *
     * @param subscriber Subscriber
     * @param type       valid: 有效的 used: 已使用的 expired:已过期的
     * @param page       页码
     */
    void getCoupons(Subscriber<CouponData> subscriber, String type, int page);


    /**
     * 领取优惠劵
     *
     * @param subscriber Subscriber
     * @param id         优惠劵id
     */
    void obtainCoupon(Subscriber<BaseBean> subscriber, String id);

    /**
     * 兑换优惠券
     *
     * @param subscriber
     * @param id
     */

    void exchangeCoupon(Subscriber<CouponDataSingle> subscriber, String id);

    /**
     * 获取指定商品能使用的优惠券
     *
     * @param subscriber Subscriber
     * @param from       商品类型(equipment|food|nutrition|course|campaign|cart)
     * @param id         购买商品的编号数组
     */
    void getSpecifyGoodsCoupon(Subscriber<CouponData> subscriber, String from, String... id);
}
