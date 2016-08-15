package com.leyuan.support.http.api;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.NurtureBean;
import com.leyuan.support.entity.NurtureDetailBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureService {

    @GET("market/diet_equipments")
    Observable<BaseBean<List<NurtureBean>>>  getNurtures(@Query("page") int page);

    @GET("market/diet_equipments/{id}")
    Observable<BaseBean<NurtureDetailBean>>  getNurtureDetail(@Path("id") int id);
}
