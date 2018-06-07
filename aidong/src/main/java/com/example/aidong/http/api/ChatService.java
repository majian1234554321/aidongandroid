package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.user.UserListResult;

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
    Observable<BaseBean<UserListResult>> getUserInfo(@Field("ids[]") List<String> id);

}
