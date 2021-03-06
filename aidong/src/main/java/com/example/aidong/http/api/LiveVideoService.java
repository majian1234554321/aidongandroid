package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.video.CommentsVideoResult;
import com.example.aidong .entity.video.LiveHomeResult;
import com.example.aidong .entity.video.VideoDetailResult;
import com.example.aidong .entity.video.VideoListResult;
import com.example.aidong .entity.video.VideoRelationResult;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/3/2.
 */

public interface LiveVideoService {

    @GET("lives")
    Observable<BaseBean<LiveHomeResult.LiveHome>> getHomeLives();

    @FormUrlEncoded
    @PUT("lives/{id}")
    Observable<BaseBean<Object>> videoStatistics(@Path("id") String id, @Field("type") String type);

    //page-页码 list-类型(family-家庭 professional-专业 celebrity-名人)
    @GET("videos")
    Observable<BaseBean<VideoListResult>> getVideoList(@Query("page") String page, @Query("list") String type);

    @GET("videos/{series_id}")
    Observable<BaseBean<VideoDetailResult>> getVideoDetail(@Path("series_id") String series_id);

    //    @FormUrlEncoded
    @POST("videos/{series_id}_{video_id}/likes")
    Observable<BaseBean<Object>> addLikes(@Path("series_id") String series_id,
                                          @Path("video_id") String video_id);

    @DELETE("videos/{series_id}_{video_id}/likes")
    Observable<BaseBean<Object>> deleteLikes(@Path("series_id") String series_id,
                                             @Path("video_id") String video_id);


    @GET("videos/{series_id}_{phase}/comments")
    Observable<BaseBean<CommentsVideoResult>> getComments(@Path("series_id") String series_id,
                                                          @Path("phase") String phase, @Query("page") String page);

    @FormUrlEncoded
    @POST("videos/{series_id}_{video_id}/comments")
    Observable<BaseBean<Object>> addCommont(@Path("series_id") String series_id,
                                            @Field("content") String content, @Path("video_id") String video_id);

    @GET("videos/{id}/relations")
    Observable<BaseBean<VideoRelationResult.VideoRelation>> getVideoRelation(@Path("id") String id, @Query("page") String page);


}
