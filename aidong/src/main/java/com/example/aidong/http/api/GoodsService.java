package com.example.aidong.http.api;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.GoodsData;
import com.example.aidong .entity.data.GoodsDetailData;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .entity.data.VenuesData;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface GoodsService {

    @GET("market/products/{type}")
    Observable<BaseBean<GoodsData>> getGoods(@Path("type") String type,
                                             @Query("page") int page,
                                             @Query("cat") String catId,
                                             @Query("sort") String sort,
                                             @Query("gym") String gymId);


    @GET("market/mutable_parts/{category_id}")
    Observable<BaseBean<GoodsData>> getGoods2(@Path("category_id") String category_id,
                                             @Query("page") int page,
                                             @Query("cat") String catId,
                                             @Query("sort") String sort,
                                             @Query("gym") String gymId);









    @GET("market/mutable_parts/{category_id}")
    Observable<BaseBean<GoodsData>> getGoodsList(@Path("category_id") String category_id,
                                                 @QueryMap Map<String, String> map);



    @GET("market/products/{type}/{id}")
    Observable<BaseBean<GoodsDetailData>> getGoodsDetail(@Path("type") String type,
                                                         @Path("id") String id);

    @GET("products/{type}/{id}/gyms")
    Observable<BaseBean<VenuesData>> getDeliveryVenues(@Path("type") String type,
                                                       @Path("id") String id,
                                                       @Query("page") int page,
                                                       @Query("brand_id") String brandId,
                                                       @Query("landmark") String landmark,
                                                       @Query("area") String area);
    @FormUrlEncoded
    @POST("market/products/{type}/{skuCode}")
    Observable<BaseBean<PayOrderData>> buyGoodsImmediately(@Path("type") String type,
                                                           @Path("skuCode") String skuCode,
                                                           @Field("amount") int amount,
                                                           @Field("coupon") String coupon,
                                                           @Field("integral") String integral,
                                                           @Field("coin") String coin,
                                                           @Field("pay_type") String payType,
                                                           @Field("pick_up_way") String pickUpWay,
                                                           @Field("pick_up_id") String pickUpId,
                                                           @Field("pick_up_date") String pickUpDate,
                                                           @Field("pick_up_period") String pick_up_period,
                                                           @Field("is_food") String is_food,
                                                           @Field("recommend_coach_id") String recommendId);

    @GET("market/products/virtuals")
    Observable<BaseBean<GoodsData>> getVirtualGoodsList(@Query("product_ids") String product_ids);
}
