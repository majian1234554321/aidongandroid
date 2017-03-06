package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.user.ProfileBeanResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by user on 2017/3/4.
 */

public interface ChatService {

    @FormUrlEncoded
    @POST("chat")
    Observable<BaseBean<ProfileBeanResult>> getUserInfo(@Field("ids[]") List<String> id);

}
