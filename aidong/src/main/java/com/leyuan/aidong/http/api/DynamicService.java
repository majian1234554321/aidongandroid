package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.data.DiscoverData;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public interface DynamicService {
    @GET("dynamics")
    Observable<BaseBean<List<DynamicBean>>> getDynamics(@Query("page") int page);

    @FormUrlEncoded
    @POST("dynamics/{id}")
    Observable<BaseBean<DiscoverData>> postDynamics(@Field("content") String content);
}
