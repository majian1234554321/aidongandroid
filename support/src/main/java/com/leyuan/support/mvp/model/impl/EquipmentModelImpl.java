package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.EquipmentBean;
import com.leyuan.support.entity.EquipmentDetailBean;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.EquipmentService;
import com.leyuan.support.mvp.model.EquipmentModel;

import java.util.List;

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
    public void getEquipments(Subscriber<List<EquipmentBean>> subscriber, int page) {
        equipmentService.getEquipments(page)
                .compose(RxHelper.<List<EquipmentBean>>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getEquipmentDetail(Subscriber<EquipmentDetailBean> subscriber, int id) {
        equipmentService.getEquipmentDetail(id)
                .compose(RxHelper.<EquipmentDetailBean>transform())
                .subscribe(subscriber);
    }
}
