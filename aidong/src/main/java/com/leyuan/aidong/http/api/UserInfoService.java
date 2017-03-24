package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.UserInfoData;
import com.leyuan.aidong.entity.user.MineInfoBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public interface UserInfoService {
    @GET("mine")
    Observable<BaseBean<MineInfoBean>> getMineInfo();

    @FormUrlEncoded
    @POST("mine/setting")
    Observable<BaseBean> hideSelf(@Field("hide") String isHide);

    @FormUrlEncoded
    @PUT("mine/reset_password")
    Observable<BaseBean> updatePassword(@Field("password") String oldPassword,
                                        @Field("new_password") String new_password,
                                        @Field("confirm_password") String confirm_password);


    @GET("users/{idongId}/profile")
    Observable<BaseBean<UserInfoData>> getUserInfo(@Path("idongId") String idongId);

    @FormUrlEncoded
    @PUT("mine/profile")
    Observable<BaseBean<UserInfoData>> updateUserInfo(@Field("name") String name,
                                                      @Field("avatar") String avatar,
                                                      @Field("gender") String gender,
                                                      @Field("birthday") String birthday,
                                                      @Field("signature") String signature,
                                                      @Field("tag_address") String tag,
                                                      @Field("sport") String sport,
                                                      @Field("province") String province,
                                                      @Field("city") String city,
                                                      @Field("area") String area,
                                                      @Field("height") String height,
                                                      @Field("weight") String weight,
                                                      @Field("bust") String bust,
                                                      @Field("waist") String waist,
                                                      @Field("hip") String hip,
                                                      @Field("charm_site") String charm_site,
                                                      @Field("frequency") String frequency);

    @FormUrlEncoded
    @PUT("mine/profile")
    Observable<BaseBean<UserInfoData>> updateUserInfoWithToken(@Header("token") String token,
                                                               @Field("name") String name,
                                                               @Field("avatar") String avatar,
                                                               @Field("gender") String gender,
                                                               @Field("birthday") String birthday,
                                                               @Field("signature") String signature,
                                                               @Field("tag_address") String tag,
                                                               @Field("sport") String sport,
                                                               @Field("province") String province,
                                                               @Field("city") String city,
                                                               @Field("area") String area,
                                                               @Field("height") String height,
                                                               @Field("weight") String weight,
                                                               @Field("bust") String bust,
                                                               @Field("waist") String waist,
                                                               @Field("hip") String hip,
                                                               @Field("charm_site") String charm_site,
                                                               @Field("frequency") String frequency);


    @GET("users/{id}/dynamics")
    Observable<BaseBean<DynamicsData>> getUserDynamic(@Path("id") String id, @Query("page") int page);
}
