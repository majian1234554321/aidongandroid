package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.video.CommentsVideoResult;
import com.leyuan.aidong.entity.video.LiveHomeResult;
import com.leyuan.aidong.entity.video.VideoDetailResult;
import com.leyuan.aidong.entity.video.VideoListResult;
import com.leyuan.aidong.entity.video.VideoRelationResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/3/2.
 */

public interface LiveVideoService {

    @GET("lives")
    Observable<BaseBean<LiveHomeResult.LiveHome>> getHomeLives();

    //page-页码 list-类型(family-家庭 professional-专业 celebrity-名人)
    @GET("videos")
    Observable<BaseBean<VideoListResult>> getVideoList(@Query("page") String page, @Query("list") String type);

    @GET("videos/{series_id}")
    Observable<BaseBean<VideoDetailResult>> getVideoDetail(@Path("series_id") String series_id);

//    @FormUrlEncoded
    @POST("videos/{series_id}_{video_id}/likes")
    Observable<BaseBean<Object>> addLikes(@Path("series_id") String series_id,
                                          @Path("video_id") String video_id);

    @POST("videos/{series_id}_{video_id}/likes")
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
