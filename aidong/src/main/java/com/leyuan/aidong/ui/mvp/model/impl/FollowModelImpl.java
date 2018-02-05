package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.FollowCampaignData;
import com.leyuan.aidong.entity.data.FollowCourseData;
import com.leyuan.aidong.entity.data.FollowData;
import com.leyuan.aidong.entity.data.FollowUserData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.FollowService;
import com.leyuan.aidong.ui.mvp.model.FollowModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 关注 粉丝
 * Created by song on 2016/8/19.
 */
public class FollowModelImpl implements FollowModel {
    private FollowService followService;

    public FollowModelImpl() {
        followService = RetrofitHelper.createApi(FollowService.class);
    }

//
//    public void getUsersFollow(Subscriber<FollowUserData> subscriber, int page) {
//        getUserFollow(subscriber, "follows", page);
//    }
//
//    public void getCoachFollow(Subscriber<FollowUserData> subscriber, int page) {
//        getUserFollow(subscriber, "coaches", page);
//    }
//
//    public void getFollowList(Subscriber<FollowUserData> subscriber, int page) {
//        getUserFollow(subscriber, "following_relation", page);
//    }

    @Override
    public void getUserFollow(Subscriber<FollowUserData> subscriber, String type, int page) {
        followService.getUserFollow(type, page)
                .compose(RxHelper.<FollowUserData>transform())
                .subscribe(subscriber);
    }


    @Override
    public void getCampaignFollow(Subscriber<FollowCampaignData> subscriber, int page) {
        followService.getCampaignFollow("campaigns", page)
                .compose(RxHelper.<FollowCampaignData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getCourseFollow(Subscriber<FollowCourseData> subscriber, int page) {
        followService.getCourseFollow("courses", page)
                .compose(RxHelper.<FollowCourseData>transform())
                .subscribe(subscriber);
    }


    public void getRecommendCoachList(Subscriber<FollowUserData> subscriber, int page) {
        followService.getRecommendCoachList(page)
                .compose(RxHelper.<FollowUserData>transform())
                .subscribe(subscriber);
    }


    @Override
    public void getFollow(Subscriber<FollowData> subscriber, String type, int page) {
        followService.getFollow(type, page)
                .compose(RxHelper.<FollowData>transform())
                .subscribe(subscriber);
    }

//    @Override
//    public void addFollow(Subscriber<BaseBean> subscriber, String id) {
//        followService.addFollow(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    @Override
    public void addFollow(Subscriber<BaseBean> subscriber, String id, String type) {
        followService.addFollow(id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
//
//    @Override
//    public void cancelFollow(Subscriber<BaseBean> subscriber, String id) {
//        followService.cancelFollow(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    @Override
    public void cancelFollow(Subscriber<BaseBean> subscriber, String id, String type) {
        followService.cancelFollow(id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
