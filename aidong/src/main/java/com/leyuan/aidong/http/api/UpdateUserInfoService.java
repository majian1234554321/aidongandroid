package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.result.LoginResult;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import rx.Observable;

public interface UpdateUserInfoService {
    @Multipart
    @PUT("users")
        //    Observable<BaseBean<LoginResult>> updateInfo(@Part("avatar\"; filename=\"image.png\"") RequestBody file);

    Observable<BaseBean<LoginResult>> updateInfo(@Part MultipartBody.Part photo);

    //    Call<ResponseBody> updateInfo(@Part MultipartBody.Part photo);
}
