package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.GoodsData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 推荐
 * Created by song on 2017/1/19.
 */
public interface RecommendService {

    @GET("recommends/{type}")
    Observable<BaseBean<GoodsData>> getRecommendGoods(@Path("type") String type, @Query("page") int page);
}
