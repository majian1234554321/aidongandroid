package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.EquipmentData;
import com.leyuan.aidong.entity.data.EquipmentDetailData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 装备
 * Created by song on 2016/8/23.
 */
public interface EquipmentService {

    @GET("market/equipments")
    Observable<BaseBean<EquipmentData>> getEquipments(@Query("page") int page);

    @GET("market/equipments/{id}")
    Observable<BaseBean<EquipmentDetailData>>  getEquipmentDetail(@Path("id") String id);
}