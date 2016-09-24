package com.example.aidong.http.api;


import com.example.aidong.entity.BaseBean;
import com.example.aidong.entity.BrandBean;
import com.example.aidong.entity.data.BannerData;
import com.example.aidong.entity.data.HomeData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 首页
 * Created by pc on 2016/8/2.
 */
public interface HomeService {

    @GET("home")
    Observable<BaseBean<HomeData>> getRecommendList(@Query("page") int page);

    @GET("home/{id}")
    Observable<BaseBean<BrandBean>> getTypeDetail(@Path("id") int id, @Query("page") int page);

    @GET("banners")
    Observable<BaseBean<BannerData>> getBanners(@Query("site") String site);
}
