package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.user.VersionResult;

import retrofit2.http.GET;
import rx.Observable;

public interface CheckVersion {
    @GET("app/android/ver")
    Observable<BaseBean<VersionResult>> checkVersion();
}
