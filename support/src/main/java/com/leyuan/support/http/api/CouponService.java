package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.CouponData;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 优惠劵
 * Created by song on 2016/9/14.
 */
public interface CouponService {

    @GET("mine/coupons/{type}")
    Observable<BaseBean<CouponData>> getCoupons(@Path("type") String type,@Query("page") int page);


    @POST("mine/coupons")
    Observable<BaseBean> obtainCoupon(@Field("coupon_id") String id);
}
