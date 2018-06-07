package com.example.aidong.http.api;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.DiscoverData;
import com.example.aidong .entity.data.DiscoverNewsData;
import com.example.aidong .entity.data.DiscoverUserData;
import com.example.aidong .entity.data.DiscoverVenuesData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 发现
 * Created by song on 2016/8/2.
 */
public interface DiscoverService {

    //首页
    @GET("discoveries")
    Observable<BaseBean<DiscoverData>> getDiscover();

    //场馆
    @GET("discoveries/gyms")
    Observable<BaseBean<DiscoverVenuesData>> getVenues(@Query("lat") double lat, @Query("lng") double lng, @Query("page") int page);

    //用户
    @GET("discoveries/persons")
    Observable<BaseBean<DiscoverUserData>> getUsers(@Query("lat") double lat, @Query("lng") double lng, @Query("page") int page, @Query("gender") String gender, @Query("type") String type);

    //新闻
    @GET("discoveries/news")
    Observable<BaseBean<DiscoverNewsData>> getNews(@Query("page") int page);
}
