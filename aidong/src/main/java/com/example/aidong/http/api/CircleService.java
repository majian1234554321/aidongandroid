package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.CircleData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by user on 2018/1/31.
 */

public interface CircleService {


    @POST("dynamics/recommend_circle")
    Observable<BaseBean<CircleData>> getRecommendCircle();

    @FormUrlEncoded
    @POST("dynamics/search_circle")
    Observable<BaseBean<CircleData>> searchCircle(@Field("cname") String name);
}
