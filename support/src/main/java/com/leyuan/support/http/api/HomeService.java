package com.leyuan.support.http.api;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.BrandBean;
import com.leyuan.support.entity.HomeBean;
import com.leyuan.support.entity.SearchResultBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 首页
 * Created by pc on 2016/8/2.
 */
public interface HomeService {

    @GET("home")
    Observable<BaseBean<List<HomeBean>>> getRecommendList(@Query("page") int page);

    @GET("home/{id}")
    Observable<BaseBean<BrandBean>> getTypeDetail(@Path("id") int id, @Query("page") int page);

    @POST("search")
    Observable<BaseBean<SearchResultBean>> search(@Field("keyword") String keyword, @Field("cat") String category, @Field("page") String page);

}
