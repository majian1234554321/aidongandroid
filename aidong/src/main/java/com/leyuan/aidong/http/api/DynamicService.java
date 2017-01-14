package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CommentData;
import com.leyuan.aidong.entity.data.DiscoverData;
import com.leyuan.aidong.entity.data.DynamicsData;

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
    @GET("dynamics")
    Observable<BaseBean<DynamicsData>> getDynamics(@Query("page") int page);

    @FormUrlEncoded
    @POST("dynamics")
    Observable<BaseBean<DiscoverData>> postDynamic(@Field("content") String content,
                                                    @Field("video") String video,
                                                    @Field("image[]") String... image);

    @FormUrlEncoded
    @POST("dynamics/{id}/comments")
    Observable<BaseBean> addComment(@Path("id") String id,@Field("content") String content);



    @GET("dynamics/{id}/comments")
    Observable<BaseBean<CommentData>> getComments(@Path("id") String id, @Query("page") int page);



    @FormUrlEncoded
    @POST("dynamics/{id}/like")
    Observable<BaseBean> addLike(@Path("id") String id);



    @GET("dynamics/{id}/likes")
    Observable<BaseBean<CommentData>> getLikes(@Path("id") String id);

}


