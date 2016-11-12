package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.SystemBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public interface SystemService {
    @GET("system")
    Observable<BaseBean<SystemBean>> getSystemInfo(@Query("os") String os);
}
