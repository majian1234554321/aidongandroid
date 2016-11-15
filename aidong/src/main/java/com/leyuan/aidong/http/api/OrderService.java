package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.OrderData;
import com.leyuan.aidong.entity.data.OrderDetailData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public interface OrderService {

    @GET("mine/orders")
    Observable<BaseBean<OrderData>> getOrders(@Query("list") String list,@Query("page") int page);

    @GET("mine/orders/{id}")
    Observable<BaseBean<OrderDetailData>> getOrderDetail(@Path("id") String id);

}