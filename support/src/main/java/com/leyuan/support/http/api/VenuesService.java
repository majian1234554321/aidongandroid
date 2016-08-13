package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.entity.VenuesDetailBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 场馆
 */
public interface VenuesService {

    /**
     * 获取场馆列表
     * @param page 当前页数
     * @return 场馆集合
     */
    @GET("gyms")
    Observable<BaseBean<List<VenuesBean>>> getVenues(@Query("page") int page);

    /**
     * 获取场馆列表
     * @param id 场馆id
     * @return 场馆详情实体
     */
    @GET("gyms/{id}")
    Observable<BaseBean<VenuesDetailBean>> getVenuesDetail(@Part("id") int id);



}
