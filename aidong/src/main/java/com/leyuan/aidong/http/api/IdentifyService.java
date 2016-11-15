package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.UserCoach;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface IdentifyService {

    @FormUrlEncoded
    @POST("captcha/regist")
    Observable<BaseBean<UserCoach>> regitserIdentify(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("captcha/forget")
    Observable<BaseBean<UserCoach>> foundIdentify(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("captcha/binding")
    Observable<BaseBean<UserCoach>> bindingMobile(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("captcha/check")
    Observable<BaseBean<UserCoach>> checkIdentify(@Field("token") String token, @Field("captcha") String captcha,
                                             @Field("password") String password);

    @FormUrlEncoded
    @POST("captcha_image/{mobile}")
    Observable<BaseBean<UserCoach>> checkCaptchaImage(@Path("mobile") String mobile,@Field("captcha") String captcha);

}
