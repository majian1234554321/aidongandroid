package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.data.EquipmentData;
import com.leyuan.support.entity.data.EquipmentDetailData;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.EquipmentService;
import com.leyuan.support.mvp.model.EquipmentModel;
import com.leyuan.support.util.LogUtil;

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
    public void getEquipmentDetail(Subscriber<EquipmentDetailData> subscriber, int id) {
        equipmentService.getEquipmentDetail(id)
                .compose(RxHelper.<EquipmentDetailData>transform())
                .subscribe(subscriber);
    }
}
