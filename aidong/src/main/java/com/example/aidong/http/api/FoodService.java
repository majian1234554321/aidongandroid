package com.example.aidong.http.api;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.FoodAndVenuesBean;
import com.example.aidong .entity.data.FoodDetailData;
import com.example.aidong .entity.data.VenuesData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 健康餐饮
 * Created by pc on 2016/8/2.
 */
public interface FoodService {

    @GET("market/foods")
    Observable<BaseBean<FoodAndVenuesBean>>getFoods(@Query("page") int page);

    @GET("market/foods/{id}")
    Observable<BaseBean<FoodDetailData>>getFoodDetail(@Path("id") String id);

    //生成健康餐饮订单
    @FormUrlEncoded
    @POST("market/foods/{id}")
    Observable<BaseBean> createFoodOrder(@Path("id") int id, @Field("id") int itemId, @Field("amount") int amount,
                                         @Field("coupon") int coupon, @Field("integral") int integral, @Field("pay_type") int payType,
                                         @Field("contact_name") int contactName, @Field("contact_mobile") int contactMobile);


    @GET("foods/{sku_code}/gyms")
    Observable<BaseBean<VenuesData>> getDeliveryVenues(@Path("sku_code") String skuCode, @Query("page") int page);

    @POST("foods/{skuCode}")
    Observable<BaseBean> buyFoodImmediately(@Path("skuCode") String skuCode,
                                                 @Field("amount") int amount,
                                                 @Field("pick_up") int pickUp,
                                                 @Field("pick_up_id") int pickUpId);
}
