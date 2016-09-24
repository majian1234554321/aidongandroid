package com.example.aidong.http.api;

import com.example.aidong.entity.BaseBean;
import com.example.aidong.entity.data.AddressData;
import com.example.aidong.entity.data.AddressListData;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 我的地址
 * Created by song on 2016/9/21.
 */
public interface AddressService{

    @GET("mine/addresses")
    Observable<BaseBean<AddressListData>> getAddress();

    @FormUrlEncoded
    @POST("mine/addresses")
    Observable<BaseBean<AddressData>> addAddress(@Field("name") String name, @Field("phone") String phone, @Field("address") String address);

    @PUT("mine/addresses/{id}")
    Observable<BaseBean<AddressData>> updateAddress(@Path("id") String id ,@Field("name") String name, @Field("phone") String phone, @Field("address") String address);

    @DELETE("mine/addresses/{id}")
    Observable<BaseBean> deleteAddress(@Path("id") String id);
}
