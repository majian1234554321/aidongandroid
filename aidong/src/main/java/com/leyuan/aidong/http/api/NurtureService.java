package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.NurtureData;
import com.leyuan.aidong.entity.data.NurtureDetailData;
import com.leyuan.aidong.entity.data.VenuesData;

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
    Observable<BaseBean<NurtureData>> getNurtures(@Query("page") int page,
                                                   @Query("brand_id") String brandId,
                                                   @Query("price") String priceSort,
                                                   @Query("order_count") String countSort,
                                                   @Query("heat") String heatSort);

    @GET("market/nutrition/{id}")
    Observable<BaseBean<NurtureDetailData>> getNurtureDetail(@Path("id") String id);

    @GET("nutrition/{sku_code}/gyms")
    Observable<BaseBean<VenuesData>> getDeliveryVenues(@Path("sku_code") String skuCode,int page);
}
