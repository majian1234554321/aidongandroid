package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 我的照片墙
 * Created by song on 2017/2/20.
 */
public interface PhotoWallService {

    @FormUrlEncoded
    @POST("mine/photos")
    Observable<BaseBean> addPhotos(@Field("photo[]") String... photo);

    @DELETE("mine/photos/{id}")
    Observable<BaseBean> deletePhotos(@Path("id") String id);

}
