package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.model.result.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import rx.Observable;

public interface UpdateDeviceService {

    @FormUrlEncoded
    @PUT("device")
    Observable<BaseBean<LoginResult>> updateDevice(@Field("device_type") String device_type, @Field("device_token") String device_token);
}
