package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
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
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureService {

    @GET("market/nutrition")
    Observable<BaseBean<NurtureData>> getNurtures(@Query("page") int page,
                                                   @Query("cat") String catId,
                                                   @Query("sort") String sort,
                                                   @Query("gym") String gymId);

    @GET("market/nutrition/{id}")
    Observable<BaseBean<NurtureDetailData>> getNurtureDetail(@Path("id") String id);

    @GET("nutrition/{id}/gyms")
    Observable<BaseBean<VenuesData>> getDeliveryVenues(@Path("id") String id,@Query("page")int page,
                                                       @Query("brand_id") String brandId,
                                                       @Query("landmark") String landmark);

    @FormUrlEncoded
    @POST("market/nutrition/{skuCode}")
    Observable<BaseBean<PayOrderData>> buyNurtureImmediately(@Path("skuCode") String skuCode,
                                                             @Field("amount") int amount,
                                                             @Field("coupon") String coupon,
                                                             @Field("integral") String integral,
                                                             @Field("coin") String coin,
                                                             @Field("pay_type") String payType,
                                                             @Field("pick_up_way") String pickUpWay,
                                                             @Field("pick_up_id") String pickUpId,
                                                             @Field("pick_up_date") String pickUpDate);
}
