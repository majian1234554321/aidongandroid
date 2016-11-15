package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.result.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginService {
    @FormUrlEncoded
    @POST("login/mobile")
    Observable<BaseBean<LoginResult>> login(@Field("mobile") String account, @Field("password") String password);
//                                            @Field("device_type") String device_type, @Field("device_token") String device_token);
    @FormUrlEncoded
    @POST("users/autoLogin")
    Observable<BaseBean<LoginResult>> autoLogin();

    @FormUrlEncoded
    @POST("login/sns")
    Observable<BaseBean<LoginResult>> loginSns(@Field("sns") String sns, @Field("access_token") String access_token);
}