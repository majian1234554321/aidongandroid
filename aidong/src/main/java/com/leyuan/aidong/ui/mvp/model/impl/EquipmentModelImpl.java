package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.data.EquipmentData;
import com.leyuan.aidong.entity.data.EquipmentDetailData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.EquipmentService;
import com.leyuan.aidong.ui.mvp.model.EquipmentModel;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * 装备
 * Created by song on 2016/8/15.
 */
public class EquipmentModelImpl implements EquipmentModel {
    private Context context;
    private EquipmentService equipmentService;

    public EquipmentModelImpl(Context context) {
        this.context = context;
        if(equipmentService == null) {
            equipmentService = RetrofitHelper.createApi(EquipmentService.class);
        }
    }

    @Override
    public ArrayList<CategoryBean> getCategory() {
        return SystemInfoUtils.getEquipmentCategory(context);
    }

    @Override
    public void getEquipments(Subscriber<EquipmentData> subscriber, int page, String brandId,
                              String priceSort, String countSort, String heatSort) {
        equipmentService.getEquipments(page,brandId,priceSort,countSort,heatSort)
                .compose(RxHelper.<EquipmentData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getEquipmentDetail(Subscriber<EquipmentDetailData> subscriber, String id) {
        equipmentService.getEquipmentDetail(id)
                .compose(RxHelper.<EquipmentDetailData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page) {
        equipmentService.getDeliveryVenues(skuCode,page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }
}
