package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public interface CartService {

    /**
     * 获取购物车列表
     * @return 商品
     */
    @GET("mine/cart")
    Observable<BaseBean<List<GoodsBean>>> getCart();

    /**
     * 添加商品到购物车
     * @param sku_code  sku_code
     * @param amount 数量
     * @return
     */
    @FormUrlEncoded
    @POST("mine/cart")
    Observable<BaseBean> addCart(@Field("sku_code") String sku_code, @Field("amount") int amount,@Field("gymId") String gymId);

    /**
     * 删除购物车中商品
     * @param ids 商品id 多个用逗号隔开
     * @return
     */
    @DELETE("mine/cart/{ids}")
    Observable<BaseBean> deleteCart(@Path("ids") String ids);

    /**
     * 修改商品数量
     * @param id 商品id
     * @param amount 数量
     * @return
     */
    @FormUrlEncoded
    @PUT("mine/cart/{id}")
    Observable<BaseBean> updateCart(@Path("id") String id,@Field("amount") int amount);
}
