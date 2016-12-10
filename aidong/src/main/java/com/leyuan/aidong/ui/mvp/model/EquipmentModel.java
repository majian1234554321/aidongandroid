package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.data.EquipmentData;
import com.leyuan.aidong.entity.data.EquipmentDetailData;
import com.leyuan.aidong.entity.data.VenuesData;

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
    void getEquipments(Subscriber<EquipmentData> subscriber, int page,String brandId,String priceSort,
                       String countSort,String heatSort);

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
    void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page);
}
