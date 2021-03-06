package com.example.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.data.EquipmentData;
import com.example.aidong .entity.data.EquipmentDetailData;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .entity.data.VenuesData;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.EquipmentService;
import com.example.aidong .ui.mvp.model.EquipmentModel;
import com.example.aidong .utils.SystemInfoUtils;

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
    public void getEquipments(Subscriber<EquipmentData> subscriber, int page, String brandId, String sort,String gymId) {
        equipmentService.getEquipments(page,brandId,sort,gymId)
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
    public void getDeliveryVenues(Subscriber<VenuesData> subscriber, String skuCode, int page,String brandId,String landmark) {
        equipmentService.getDeliveryVenues(skuCode,page,brandId,landmark)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void buyEquipmentImmediately(Subscriber<PayOrderData> subscriber, String skuCode,
                                        int amount, String coupon, String integral, String coin,
                                        String payType, String pickUpWay, String pickUpId,
                                        String pickUpDate) {
        equipmentService.buyEquipmentImmediately(skuCode,amount,coupon,integral,coin,payType,
                pickUpWay, pickUpId,pickUpDate)
                .compose(RxHelper.<PayOrderData>transform())
                .subscribe(subscriber);
    }
}
