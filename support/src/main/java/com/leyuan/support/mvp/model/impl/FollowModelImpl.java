package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.FollowerData;
import com.leyuan.support.entity.data.FollowingData;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.FollowService;
import com.leyuan.support.mvp.model.FollowModel;

import rx.Subscriber;

/**
 * 关注 粉丝
 * Created by song on 2016/8/19.
 */
public class FollowModelImpl implements FollowModel{
    private FollowService followService;

    public FollowModelImpl() {
        followService = RetrofitHelper.createApi(FollowService.class);
    }

    @Override
    public void getFollows(Subscriber<FollowerData> subscriber, int page) {
        followService.getFollowers(page)
                .compose(RxHelper.<FollowerData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getFollowings(Subscriber<FollowingData> subscriber, int page) {
        followService.getFollowings(page)
                .compose(RxHelper.<FollowingData>transform())
                .subscribe(subscriber);
    }



    @Override
    public void addFollow(Subscriber<BaseBean> subscriber, int id) {
        followService.addFollow(id)
                .subscribe(subscriber);
    }

    @Override
    public void cancelFollow(Subscriber<BaseBean> subscriber, int id) {
        followService.cancelFollow(id)
                .subscribe(subscriber);
    }
}
