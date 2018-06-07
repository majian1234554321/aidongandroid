package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.data.EquipmentData;
import com.example.aidong .entity.data.EquipmentDetailData;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .entity.data.VenuesData;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public interface EquipmentModel {
    /**
     * 获取装备分类信息
     * @return List<CategoryBean>
     */
    ArrayList<CategoryBean> getCategory();

    /**
     * 获取装备列表
     * @param subscriber Subscribers
     * @param page 页码
     */
    void getEquipments(Subscriber<EquipmentData> subscriber, int page,String brandId,String sort,String gymId);

    /**
     * 获取装备详情
     * @param subscriber Subscribers
     * @param id id
     */
    void getEquipmentDetail(Subscriber<EquipmentDetailData> subscriber, String id);

    /**
     * 获取装备的自提场馆
     * @param subscriber Subscribers
     * @param skuCode sku码
     * @param page 页码
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
     * @param payType 支付方式
     * @param pickUpWay 取货方式(0-快递 1-自提)
     * @param pickUpId 快递地址id
     * @param pickUpDate 自提时间
     */
    void buyEquipmentImmediately(Subscriber<PayOrderData> subscriber, String skuCode, int amount,
                               String coupon, String integral, String coin, String payType,
                               String pickUpWay, String pickUpId, String pickUpDate
    );
}
