package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.EquipmentData;
import com.leyuan.aidong.entity.data.EquipmentDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.entity.data.VenuesData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 装备
 * Created by song on 2016/8/23.
 */
public interface EquipmentService {

    @GET("market/equipments")
    Observable<BaseBean<EquipmentData>> getEquipments(@Query("page") int page,
                                                      @Query("cat") String catId,
                                                      @Query("sort") String sort,
                                                      @Query("gymId") String gymId);

    @GET("market/equipments/{id}")
    Observable<BaseBean<EquipmentDetailData>>  getEquipmentDetail(@Path("id") String id);

    @GET("equipments/{id}/gyms")
    Observable<BaseBean<VenuesData>> getDeliveryVenues(@Path("id") String skuCode,@Query("page") int page);

    @FormUrlEncoded
    @POST("market/equipments/{skuCode}")
    Observable<BaseBean<PayOrderData>> buyEquipmentImmediately(@Path("skuCode") String skuCode,
                                                               @Field("amount") int amount,
                                                               @Field("coupon") String coupon,
                                                               @Field("integral") String integral,
                                                               @Field("coin") String coin,
                                                               @Field("pay_type") String payType,
                                                               @Field("pick_up_way") String pickUpWay,
                                                               @Field("pick_up_id") String pickUpId,
                                                               @Field("pick_up_date") String pickUpDate);
}
