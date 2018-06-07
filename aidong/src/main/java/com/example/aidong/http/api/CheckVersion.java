package com.example.aidong.http.api;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.user.VersionResult;

import retrofit2.http.GET;
import rx.Observable;

public interface CheckVersion {
    @GET("app/android/ver")
    Observable<BaseBean<VersionResult>> checkVersion();
}
