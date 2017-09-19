package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.user.VersionResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/3/6.
 */
public interface VersionService {
    @GET("i_fitness/version")
    Observable<VersionResult> getVersionInfo(@Query("os_type") String os_type);
}
