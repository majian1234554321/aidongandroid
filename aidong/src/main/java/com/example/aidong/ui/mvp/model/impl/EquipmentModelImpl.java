package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong.entity.data.EquipmentData;
import com.example.aidong.entity.data.EquipmentDetailData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.RxHelper;
import com.example.aidong.http.api.EquipmentService;
import com.example.aidong.ui.mvp.model.EquipmentModel;
import com.example.aidong.utils.LogUtil;

import rx.Subscriber;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public class EquipmentModelImpl implements EquipmentModel {
    private EquipmentService equipmentService;

    public EquipmentModelImpl() {
        equipmentService = RetrofitHelper.createApi(EquipmentService.class);
    }

    @Override
    public void getEquipments(Subscriber<EquipmentData> subscriber, int page) {
        LogUtil.d("retrofit","EquipmentModelImpl page:"+page);
        equipmentService.getEquipments(page)
                .compose(RxHelper.<EquipmentData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getEquipmentDetail(Subscriber<EquipmentDetailData> subscriber, String id) {
        equipmentService.getEquipmentDetail(id)
                .compose(RxHelper.<EquipmentDetailData>transform())
                .subscribe(subscriber);
    }
}
