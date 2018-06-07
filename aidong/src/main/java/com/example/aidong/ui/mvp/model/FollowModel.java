package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.FollowCampaignData;
import com.example.aidong .entity.data.FollowCourseData;
import com.example.aidong .entity.data.FollowData;
import com.example.aidong .entity.data.FollowUserData;

import rx.Subscriber;

/**
 * 关注 粉丝
 * Created by song on 2016/8/19.
 */
public interface FollowModel {


    void getUserFollow(Subscriber<FollowUserData> subscriber, String type, int page);


    void getCampaignFollow(Subscriber<FollowCampaignData> subscriber, int page);

    void getCourseFollow(Subscriber<FollowCourseData> subscriber, int page);

    /**
     * 关注和粉丝
     * @param subscriber Subscriber
     * @param type followings:我关注的人 followers:关注我的人
     * @param page 页码
     */
    void getFollow(Subscriber<FollowData> subscriber, String type, int page);


//    /**
//     * 添加关注
//     * @param subscriber Subscriber
//     * @param id 用户id
//     */
//    void addFollow(Subscriber<BaseBean> subscriber,String id);
//
//    /**
//     * 取消关注
//     * @param subscriber Subscriber
//     * @param id 用户id
//     */
//    void cancelFollow(Subscriber<BaseBean> subscriber,String id);

    void getFollowers(Subscriber<FollowData> subscriber, int page);

    void getFollowCache(Subscriber<FollowData> subscriber);

    void addFollow(Subscriber<BaseBean> subscriber, String id, String type);

    void cancelFollow(Subscriber<BaseBean> subscriber, String id, String type);
}
