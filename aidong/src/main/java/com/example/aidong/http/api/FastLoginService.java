package com.example.aidong.http.api;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.model.result.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface FastLoginService {

    @FormUrlEncoded
    @POST("captcha/quick")
    Observable<BaseBean<LoginResult>> getIdentify(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("captcha/check")
    Observable<BaseBean<LoginResult>> checkIdentify(@Field("mobile") String mobile, @Field("code") String code);
}
