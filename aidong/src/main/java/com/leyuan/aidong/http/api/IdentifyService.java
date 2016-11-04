package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.UserCoach;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface IdentifyService {

    @FormUrlEncoded
    @POST("captcha")
    Observable<BaseBean<UserCoach>> regitserIdentify(@Field("mobile") String mobile);

    @FormUrlEncoded
    @PUT("captcha")
    Observable<BaseBean<UserCoach>> foundIdentify(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("captcha/check")
    Observable<BaseBean<UserCoach>> checkIdentify(@Field("mobile") String mobile, @Field("code") String code,
                                             @Field("password") String password);

}
