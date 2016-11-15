package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.FoodAndVenuesBean;
import com.leyuan.aidong.entity.data.FoodDetailData;

import retrofit2.http.Field;
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
    @POST("market/foods/{id}")
    Observable<BaseBean> createFoodOrder(@Path("id") int id, @Field("item_id") int itemId, @Field("amount") int amount,
                                         @Field("coupon") int coupon, @Field("integral") int integral, @Field("pay_type") int pay_type,
                                         @Field("contact_name") int contact_name, @Field("contact_mobile") int contact_mobile);
}