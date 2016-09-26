package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.FollowData;

import rx.Subscriber;

/**
 * 关注 粉丝
 * Created by song on 2016/8/19.
 */
public interface FollowModel {

    /**
     * 关注和粉丝
     * @param subscriber Subscriber
     * @param type followings:我关注的人 followers:关注我的人
     * @param page 页码
     */
    void getFollow(Subscriber<FollowData> subscriber, String type, int page);

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
