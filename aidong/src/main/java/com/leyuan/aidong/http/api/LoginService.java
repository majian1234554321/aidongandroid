package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.result.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginService {
    @FormUrlEncoded
    @POST("users/login")
    Observable<BaseBean<LoginResult>> login(@Field("mobile") String account, @Field("password") String password,
                                            @Field("device_type") String device_type, @Field("device_token") String device_token);

    @POST("users/autoLogin")
    Observable<BaseBean<LoginResult>> autoLogin();
}
