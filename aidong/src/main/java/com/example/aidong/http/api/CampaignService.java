package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.AppointmentDetailData;
import com.example.aidong .entity.data.CampaignData;
import com.example.aidong .entity.data.CampaignDetailData;
import com.example.aidong .entity.data.PayOrderData;

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
    Observable<BaseBean<CampaignData>> getCampaigns(@Query("page") int page,@Query("list") String list);

    @GET("market/campaigns/{id}")
    Observable<BaseBean<CampaignDetailData>> getCampaignDetail(@Path("id") String id);

    @FormUrlEncoded
    @POST("market/campaigns/{id}")
    Observable<BaseBean<PayOrderData>> buyCampaign(@Path("id") String id,
                                                   @Field("coupon") String couponId,
                                                   @Field("integral") float integral,
                                                   @Field("pay_type") String payType,
                                                   @Field("contact_name") String contactName,
                                                   @Field("contact_mobile") String contactMobile,
                                                   @Field("amount") String amount,
                                                   @Field("remark") String remark);

    @GET("market/campaigns/{id}/appointment")
    Observable<BaseBean<AppointmentDetailData>> getCampaignAppointDetail(@Path("id") String id);
}
