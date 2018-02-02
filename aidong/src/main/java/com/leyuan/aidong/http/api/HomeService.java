package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.BrandData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.entity.data.HomeDataOld;
import com.leyuan.aidong.entity.data.UserData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 首页
 * Created by pc on 2016/8/2.
 */
public interface HomeService {

//    @GET("home")
    @GET("market_home")
    Observable<BaseBean<HomeDataOld>> getRecommendList(@Query("page") int page, @Query("list") String list);

    @GET("market_home/{id}")
    Observable<BaseBean<BrandData>> getTypeDetail(@Path("id") String id, @Query("page") int page);


    @GET("recommends_home")
    Observable<BaseBean<HomeData>> getRecommendList();

    @GET("more_coach")
    Observable<BaseBean<UserData>> getRecommendCoachList(@Query("page") int page);






}
