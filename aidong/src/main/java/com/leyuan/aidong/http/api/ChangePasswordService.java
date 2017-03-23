package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import rx.Observable;

public interface ChangePasswordService {

    @FormUrlEncoded
    @PUT("mine/reset_password")
    Observable<BaseBean> changePassword(@Field("password") String password,
                                                     @Field("new_password") String new_password,
                                                     @Field("confirm_password") String re_passsword);

}
