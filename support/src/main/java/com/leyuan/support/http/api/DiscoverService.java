package com.leyuan.support.http.api;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.UserBean;
import com.leyuan.support.entity.VenuesBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 发现
 * Created by song on 2016/8/2.
 */
public interface DiscoverService {

    @GET("discovery/gyms")
    Observable<BaseBean<List<VenuesBean>>> getVenues(@Query("lat") float lat, @Query("lng") float lng, @Query("page") int page);

    @GET("discovery/persons")
    Observable<BaseBean<List<UserBean>>> getUsers(@Query("lat") float lat, @Query("lng") float lng, @Query("page") int page, @Query("gender") String gender, @Query("type") int type);
}