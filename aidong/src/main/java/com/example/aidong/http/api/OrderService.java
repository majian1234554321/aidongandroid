package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CartIdBean;
import com.example.aidong .entity.ExpressBean;
import com.example.aidong .entity.OrderDetailExpressBean;
import com.example.aidong .entity.data.OrderData;
import com.example.aidong .entity.data.OrderDetailData;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 订单
 * Created by song on 2016/9/1.
 */
public interface OrderService {

    @GET("mine/orders")
    Observable<BaseBean<OrderData>> getOrders(@Query("list") String list, @Query("page") int page);

    @GET("mine/orders/{id}")
    Observable<BaseBean<OrderDetailData>> getOrderDetail(@Path("id") String id);

    @POST("mine/orders/{id}/cancel")
    Observable<BaseBean> cancelOrder(@Path("id") String id);

    @FormUrlEncoded
    @POST("mine/orders/{id}/feedback")
    Observable<BaseBean> feedbackOrder(@Path("id") String id,
                                       @Field("type") String type,
                                       @Field("item[]") String[] item,
                                       @Field("reason") String content,
                                       @Field("image[]") String[] image,
                                       @Field("address_id") String address);

    @POST("mine/orders/{id}/confirm")
    Observable<BaseBean> confirmOrder(@Path("id") String id);

    @DELETE("mine/orders/{id}")
    Observable<BaseBean> deleteOrder(@Path("id") String id);

    @FormUrlEncoded
    @POST("mine/cart/readd")
    Observable<BaseBean<CartIdBean>> reBuyOrder(@Field("id") String id);

    @GET("mine/express")
    Observable<BaseBean<ExpressBean>> getExpressInfo(@Query("order_id") String orderId);

    @GET(" mine/simple_express")
    Observable<BaseBean<OrderDetailExpressBean>> getOrderDetailExpressInfo(@Query("order_id") String orderId);
}
