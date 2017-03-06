package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.VersionInformation;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by user on 2017/3/6.
 */
public interface VersionService {
    @GET("coachInfo/getAppUpdate.json")
    Observable<BaseBean<VersionInformation>> getVersionInfo();
}
