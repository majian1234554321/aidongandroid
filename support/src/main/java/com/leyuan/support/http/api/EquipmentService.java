package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.EquipmentBean;
import com.leyuan.support.entity.EquipmentDetailBean;

import java.util.List;

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
    Observable<BaseBean<List<EquipmentBean>>> getEquipments(@Query("page") int page);

    @GET("market/equipments/{id}")
    Observable<BaseBean<EquipmentDetailBean>>  getEquipmentDetail(@Path("id") int id);
}
