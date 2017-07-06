package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.ConfigUrlResult;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by user on 2017/7/5.
 */

public interface ConfigService {

    @GET("app_switch")
    Observable<BaseBean<ConfigUrlResult>> getUrlConfig();
}
