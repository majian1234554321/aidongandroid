package com.leyuan.support.http.api;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.DiscoverUserData;
import com.leyuan.support.entity.data.DiscoverVenuesData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 发现
 * Created by song on 2016/8/2.
 */
public interface DiscoverService {

    @GET("discovery/gyms")
    Observable<BaseBean<DiscoverVenuesData>> getVenues(@Query("lat") double lat, @Query("lng") double lng, @Query("page") int page);

    @GET("discovery/persons")
    Observable<BaseBean<DiscoverUserData>> getUsers(@Query("lat") double lat, @Query("lng") double lng, @Query("page") int page, @Query("gender") String gender, @Query("type") String type);
}
