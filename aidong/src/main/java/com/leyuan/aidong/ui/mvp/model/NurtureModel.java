package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.VenuesData;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureModel {
    /**
     * 获取营养品分类信息
     *
     * @return List<CategoryBean>
     */
    ArrayList<CategoryBean> getCategory();

    /**
     * 获取营养品列表
     *
     * @param subscriber Subscribers
     * @param page       页码
     */
    void getNurtures(Subscriber<NurtureData> subscriber, int page, String brandId, String sort,String gymId);

    /**
     * 获取营养品详情
     *
     * @param subscriber Subscribers
     * @param id         id
     */
    void getNurtureDetail(Subscriber<NurtureDetailData> subscriber, String id);

    /**
     * 获取营养品的自提场馆
     *
     * @param subscriber Subscribers
     * @param skuCode    sku码
     * @param page       页码
     */
    void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page,String brandId,String landmark);

    /**
     * 立即购买
     * @param subscriber Subscriber
     * @param skuCode skuCode
     * @param amount 数量
     * @param coupon  优惠券
     * @param integral 积分
     * @param coin  爱币
     * @param payType   支付方式
     * @param pickUpWay 取货方式(0-快递 1-自提)
     * @param pickUpId 快递地址id
     * @param pickUpDate 自提时间
     */
    void buyNurtureImmediately(Subscriber<PayOrderData> subscriber, String skuCode, int amount,
                               String coupon, String integral, String coin, String payType,
                               String pickUpWay, String pickUpId, String pickUpDate
    );
}
