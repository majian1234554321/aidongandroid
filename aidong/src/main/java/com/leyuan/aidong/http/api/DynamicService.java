package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CommentData;
import com.leyuan.aidong.entity.data.DiscoverData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.DynamicsSingleData;
import com.leyuan.aidong.entity.data.LikeData;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public interface DynamicService {


    @GET("dynamics/{id}")
    Observable<BaseBean<DynamicsSingleData>> getDynamicDetail(@Path("id") String id);

    @GET("dynamics")
    Observable<BaseBean<DynamicsData>> getDynamics(@Query("page") int page);

    @GET("dynamics/related_dynamic")
    Observable<BaseBean<DynamicsData>> getRelativeDynamics(@Query("type") String type,
                                                   @Query("link_id") String link_id,
                                                   @Query("page") int page);


    @GET("mine/following_dynamic")
    Observable<BaseBean<DynamicsData>> getDynamicsFollow(@Query("page") int page);


    @POST("dynamics")
    Observable<BaseBean<DynamicsData>> postImageDynamic(@Body RequestBody requestBody);


    @FormUrlEncoded
    @POST("dynamics")
    Observable<BaseBean<DiscoverData>> postImageDynamic(@Field("content") String content,
                                                        @Field("type") String type,
                                                        @Field("link_id") String link_id,
                                                        @Field("position_name") String position_name,
                                                        @Field("latitude") String latitude,
                                                        @Field("longitude") String longitude,
                                                        @Field("image[]") String... image);



    @FormUrlEncoded
    @POST("dynamics")
    Observable<BaseBean<DiscoverData>> postVideoDynamic(@Field("content") String content,
                                                        @Field("video") String video,
                                                        @Field("qiniu") String qiniu,
                                                        @Field("type") String type,
                                                        @Field("link_id") String link_id,
                                                        @Field("position_name") String position_name,
                                                        @Field("latitude") String latitude,
                                                        @Field("longitude") String longitude,
                                                        @Field("image[]") String... image);

    @FormUrlEncoded
    @POST("dynamics/{id}/comments")
    Observable<BaseBean> addComment(@Path("id") String id, @Field("content") String content);

    @POST("dynamics/{id}/comments")
    Observable<BaseBean> addComment(@Path("id") String id, @Body RequestBody requestBody);

    @GET("dynamics/{id}/comments")
    Observable<BaseBean<CommentData>> getComments(@Path("id") String id, @Query("page") int page);

    @POST("dynamics/{id}/likes")
    Observable<BaseBean> addLike(@Path("id") String id);

    @DELETE("dynamics/{id}/likes")
    Observable<BaseBean> cancelLike(@Path("id") String id);

    @GET("dynamics/{id}/likes")
    Observable<BaseBean<LikeData>> getLikes(@Path("id") String id, @Query("page") int page);

    @FormUrlEncoded
    @POST("dynamics/{id}/reports")
    Observable<BaseBean> reportDynamic(@Path("id") String id, @Field("types") String type);

    @DELETE("dynamics/{id}")
    Observable<BaseBean> deleteDynamic(@Path("id") String id);


}


