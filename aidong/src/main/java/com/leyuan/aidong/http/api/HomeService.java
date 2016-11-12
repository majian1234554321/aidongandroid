package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.BannerData;
import com.leyuan.aidong.entity.data.BrandData;
import com.leyuan.aidong.entity.data.HomeData;

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
    Observable<BaseBean<BrandData>> getTypeDetail(@Path("id") String id, @Query("page") int page);

    @GET("banners")
    Observable<BaseBean<BannerData>> getBanners(@Query("site") String site);
}
