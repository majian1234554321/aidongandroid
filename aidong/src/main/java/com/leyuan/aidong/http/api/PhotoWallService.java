package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 我的照片墙
 * Created by song on 2017/2/20.
 */
public interface PhotoWallService {

    @FormUrlEncoded
    @POST("mine/photowall")
    Observable<BaseBean> addPhotos(@Field("photo[]") String... photo);

    @FormUrlEncoded
    @PUT("mine/photowall/{id}")
    Observable<BaseBean> deletePhotos(@Path("id") String id);

}
