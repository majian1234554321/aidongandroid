package com.leyuan.support.http.api;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.HealthyFoodBean;

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
public interface HealthyFoodService {

    @GET("market/foods")
    Observable<BaseBean<HealthyFoodBean>>getFoods(@Query("page") int page);

    @GET("market/foods/{id}")
    Observable<BaseBean<HealthyFoodBean>>getFoodDetail(@Path("id") int id);

    @POST("market/foods/{id}")
    Observable<BaseBean> buyFood(@Path("id") int id, @Field("item_id") int itemId, @Field("amount") int amount,
                                 @Field("coupon") int coupon, @Field("integral") int integral, @Field("pay_type") int pay_type,
                                 @Field("contact_name") int contact_name, @Field("contact_mobile") int contact_mobile);
}
