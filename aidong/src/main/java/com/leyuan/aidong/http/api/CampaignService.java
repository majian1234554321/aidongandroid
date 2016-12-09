package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CampaignDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignService {

    @GET("market/campaigns")
    Observable<BaseBean<CampaignData>> getCampaigns(@Query("page") int page);

    @GET("market/campaigns/{id}")
    Observable<BaseBean<CampaignDetailData>> getCampaignDetail(@Path("id") String id);

    @FormUrlEncoded
    @POST("market/campaigns/{id}")
    Observable<BaseBean<PayOrderData>> buyCampaign(@Path("id") String id,
                                                   @Field("coupon") String couponId,
                                                   @Field("integral") float integral,
                                                   @Field("pay_type") String payType,
                                                   @Field("contact_name") String contactName,
                                                   @Field("contact_mobile") String contactMobile);

}
