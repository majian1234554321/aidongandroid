package com.example.aidong.http.api;

import com.example.aidong .entity.user.VersionResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/3/6.
 */
public interface VersionService {
    @GET("i_fitness/android")
    Observable<VersionResult> getVersionInfo(@Query("os_type") String os_type);

//    @GET("mine/version")
//    Observable<BaseBean<VersionInformation>> getVersionInfo(@Query("os_type") String os_type);
}
