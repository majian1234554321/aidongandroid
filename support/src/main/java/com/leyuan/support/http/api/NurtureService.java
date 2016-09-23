package com.leyuan.support.http.api;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.NurtureData;
import com.leyuan.support.entity.data.NurtureDetailData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureService {

    @GET("market/nutrition")
    Observable<BaseBean<NurtureData>>  getNurtures(@Query("page") int page);

    @GET("market/nutrition/{id}")
    Observable<BaseBean<NurtureDetailData>>  getNurtureDetail(@Path("id") String id);
}
