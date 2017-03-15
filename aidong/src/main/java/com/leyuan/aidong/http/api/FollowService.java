package com.leyuan.aidong.http.api;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.FollowData;

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
    @GET("mine/{type}")
    Observable<BaseBean<FollowData>> getFollow(@Path("type") String type, @Query("page") int page);

    /**
     * 添加关注
     * @param id 关注的用户id
     * @return 状态
     */
    @FormUrlEncoded
    @POST("mine/followings")
    Observable<BaseBean> addFollow(@Field("following_id") String id);


    /**
     * 取消关注
     * @param id 取消关注的用户id
     * @return 状态
     */
    @DELETE("mine/followings/{id}")
    Observable<BaseBean> cancelFollow(@Path("id") String id);
}
