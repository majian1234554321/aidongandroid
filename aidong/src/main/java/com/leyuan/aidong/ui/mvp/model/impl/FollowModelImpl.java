package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.FollowData;
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
public class FollowModelImpl implements FollowModel{
    private FollowService followService;

    public FollowModelImpl() {
        followService = RetrofitHelper.createApi(FollowService.class);
    }

    @Override
    public void getFollow(Subscriber<FollowData> subscriber, String type, int page) {
        followService.getFollow(type,page)
                .compose(RxHelper.<FollowData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void addFollow(Subscriber<BaseBean> subscriber, int id) {
        followService.addFollow(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void cancelFollow(Subscriber<BaseBean> subscriber, int id) {
        followService.cancelFollow(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
