package com.example.aidong.http.api;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.FollowCampaignData;
import com.example.aidong .entity.data.FollowCourseData;
import com.example.aidong .entity.data.FollowData;
import com.example.aidong .entity.data.FollowUserData;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 关注
 * Created by song on 2016/8/2.
 */
public interface FollowService {

    /**
     * 关注和粉丝
     * @param page 第几页
     * @param type followings:我关注的人 followers:关注我的人
     * @return 用户集合
     */
    @GET("mine/followers")
    Observable<BaseBean<FollowData>> getFollowers( @Query("page") int page);

    @GET("mine/followings")
    Observable<BaseBean<FollowData>> getFollow(@Query("list") String type, @Query("page") int page);


    @GET("mine/followings/user_cache")
    Observable<BaseBean<FollowData>> getFollowCache();

//    /**
//     * 添加关注
//     * @param id 关注的用户id
//     * @return 状态
//     */
//    @FormUrlEncoded
//    @POST("mine/followings")
//    Observable<BaseBean> addFollow(@Field("following_id") String id);

    /**
     * 添加关注
     * @param id 关注的id
     * @param type :"user","coach","campaign","course"
     * @return
     */
    @FormUrlEncoded
    @POST("mine/followings")
    Observable<BaseBean> addFollow(@Field("following_id") String id,@Field("type") String type);

//
//    /**
//     * 取消关注
//     * @param id 取消关注的用户id
//     * @return 状态
//     */
//    @DELETE("mine/followings/{id}")
//    Observable<BaseBean> cancelFollow(@Path("id") String id);


    /**
     * 取消关注
     * @param id 取消关注的用户id
     * @return 状态
     */
    @DELETE("mine/followings/{id}")
    Observable<BaseBean> cancelFollow(@Path("id") String id,@Query("type") String type);


//    @GET("mine/followings")
//    Observable<BaseBean<FollowData>> getMyFollow(@Query("list") String type,@Query("page") int page);


    @GET("mine/followings")
    Observable<BaseBean<FollowUserData>> getUserFollow(@Query("list") String type, @Query("page") int page);

    @GET("mine/followings")
    Observable<BaseBean<FollowCampaignData>> getCampaignFollow(@Query("list") String type, @Query("page") int page);

    @GET("mine/followings")
    Observable<BaseBean<FollowCourseData>> getCourseFollow(@Query("list") String type, @Query("page") int page);

    @GET("more_coach")
    Observable<BaseBean<FollowUserData>> getRecommendCoachList(@Query("page") int page);




}
