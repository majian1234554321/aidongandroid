package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.PayOrderData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 支付
 * Created by song on 2016/11/14.
 */
public interface PayService {

    @FormUrlEncoded
    @POST("market/{type}/{id}")
    Observable<BaseBean<PayOrderData>> buyCampaignDetail(@Path("type")String type,@Path("id") String id,
                                                         @Field("coupon") String couponId,
                                                         @Field("integral") float integral,
                                                         @Field("pay_type") String payType,
                                                         @Field("contact_name") String contactName,
                                                         @Field("contact_mobile") String contactMobile);
}
