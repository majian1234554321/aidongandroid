package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.FollowerData;
import com.leyuan.support.entity.data.FollowingData;

import rx.Subscriber;

/**
 * 关注 粉丝
 * Created by song on 2016/8/19.
 */
public interface FollowModel {

    /**
     * 获取粉丝列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getFollows(Subscriber<FollowerData> subscriber, int page);

    /**
     * 获取我关注的用户列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getFollowings(Subscriber<FollowingData> subscriber, int page);

    /**
     * 添加关注
     * @param subscriber Subscriber
     * @param id 用户id
     */
    void addFollow(Subscriber<BaseBean> subscriber,int id);

    /**
     * 取消关注
     * @param subscriber Subscriber
     * @param id 用户id
     */
    void cancelFollow(Subscriber<BaseBean> subscriber,int id);
}
