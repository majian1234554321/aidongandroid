package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.EquipmentData;
import com.leyuan.aidong.entity.data.EquipmentDetailData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.EquipmentService;
import com.leyuan.aidong.ui.mvp.model.EquipmentModel;
import com.leyuan.aidong.utils.LogUtil;

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
