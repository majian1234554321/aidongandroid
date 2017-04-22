package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.VersionInformation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/3/6.
 */
public interface VersionService {
    @GET("mine/version")
    Observable<BaseBean<VersionInformation>> getVersionInfo(@Query("os_type") String os_type);
}
