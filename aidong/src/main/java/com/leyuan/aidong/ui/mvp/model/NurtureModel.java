package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
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
     * @return List<CategoryBean>
     */
    ArrayList<CategoryBean> getCategory();

    /**
     * 获取营养品列表
     * @param subscriber Subscribers
     * @param page 页码
     */
    void getNurtures(Subscriber<NurtureData> subscriber, int page,String brandId,String priceSort,
                     String countSort,String heatSort);

    /**
     * 获取营养品详情
     * @param subscriber Subscribers
     * @param id id
     */
    void getNurtureDetail(Subscriber<NurtureDetailData> subscriber, String id);

    /**
     * 获取营养品的自提场馆
     * @param subscriber Subscribers
     * @param skuCode sku码
     * @param page 页码
     */
    void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page);

    /**
     * 立即购买
     * @param subscriber Subscriber
     * @param skuCode sku码
     * @param amount 数量
     * @param pickUp 0-快递 1-自提
     * @param pickUpId 自提信息编号
     */
    void buyNurtureImmediately(Subscriber<BaseBean> subscriber,String skuCode,int amount,
                               String pickUp,String pickUpId);
}
