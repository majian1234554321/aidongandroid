package com.leyuan.support.http.api;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.UserBean;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
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
     * 我关注的人
     * @param page 第几页
     * @return 用户集合
     */
    @GET("mine/followings")
    Observable<BaseBean<List<UserBean>>> getFollowings(@Query("page") int page);

    /**
     * 关注我的人
     * @param page 第几页
     * @return 用户集合
     */
    @GET("mine/followers")
    Observable<BaseBean<List<UserBean>>> getFollowers(@Query("page") int page);

    /**
     * 添加关注
     * @param id 关注的用户id
     * @return 状态
     */
    @POST("mine/followers")
    Observable<BaseBean> addFollow(@Field("following_id") int id);


    /**
     * 取消关注
     * @param id 取消关注的用户id
     * @return 状态
     */
    @DELETE("mine/followers/{id}")
    Observable<BaseBean> cancelFollow(@Path("id") int id);
}
