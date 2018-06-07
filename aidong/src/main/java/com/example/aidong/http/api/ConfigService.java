package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.ConfigUrlResult;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by user on 2017/7/5.
 */

public interface ConfigService {

    @GET("app_switch")
    Observable<BaseBean<ConfigUrlResult>> getUrlConfig();
}
