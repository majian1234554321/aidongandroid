package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.result.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.PUT;
import rx.Observable;

public interface ChangePasswordService {

    @PUT("users/reset_password")
    Observable<BaseBean<LoginResult>> changePassword(@Field("password") String password,
                                                     @Field("new_password") String new_password,
                                                     @Field("re_passsword") String re_passsword);
}
