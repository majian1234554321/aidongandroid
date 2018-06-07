package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.model.result.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginService {
    @FormUrlEncoded
    @POST("login/mobile")
    Observable<BaseBean<LoginResult>> login(@Field("mobile") String account, @Field("password") String password,
                                            @Field("register_code") String register_code);

    //                                            @Field("device_type") String device_type, @Field("device_token") String device_token);
    @FormUrlEncoded
    @POST("login/auto")
    Observable<BaseBean<LoginResult>> autoLogin(@Field("register_code") String register_code);

    @POST("login/release")
    Observable<BaseBean<LoginResult>> exitLogin();

    @FormUrlEncoded
    @POST("login/sns")
    Observable<BaseBean<LoginResult>> loginSns(@Field("sns") String sns, @Field("code") String access_token
            , @Field("register_code") String register_code);
}
