package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.user.CouponDataSingle;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Observable<BaseBean<CouponData>> getCoupons(@Path("type") String type, @Query("page") int page);


    @FormUrlEncoded
    @POST("mine/coupons")
    Observable<BaseBean> obtainCoupon(@Field("coupon_id") String id);

    @FormUrlEncoded
    @POST("mine/coupons/exchange")
    Observable<BaseBean<CouponDataSingle>> exchangeCoupon(@Field("code") String id);

    @GET("mine/coupons")
    Observable<BaseBean<CouponData>> getSpecifyGoodsCoupon(@Query("from") String from, @Query("id[]") String... id);

    @FormUrlEncoded
    @POST("mine/coupons/valid")
    Observable<BaseBean<CouponData>> getGoodsAvailableCoupon(@Field("items[]") String... items);

    @POST("exapi/{id}/obtain_coupon")
    Observable<BaseBean<CouponData>> getGoodsTakableCoupon(@Path("id") String id);

    @FormUrlEncoded
    @POST("mine/coupons/share_coupon")
    Observable<BaseBean<ShareData>> getShareCoupon(@Field("order_no") String order_no);
//
//    @FormUrlEncoded
//    @POST("mine/coupons/valid")
//    Observable<BaseBean<CouponData>> getGoodsAvailableCoupon(@Field("items[]") ArrayList<String> items,
//                                                             @Field("gym_id[]") ArrayList<String> gym_id);


//    @Multipart
    @POST("mine/coupons/valid")
    Observable<BaseBean<CouponData>> getGoodsAvailableCoupon(@Body RequestBody requestBody);
}
