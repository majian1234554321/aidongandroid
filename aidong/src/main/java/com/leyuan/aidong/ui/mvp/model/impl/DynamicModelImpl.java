package com.leyuan.aidong.ui.mvp.model.impl;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.data.CommentData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.LikeData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.DynamicService;
import com.leyuan.aidong.ui.mvp.model.DynamicModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class DynamicModelImpl implements DynamicModel {
    private DynamicService dynamicService;

    public DynamicModelImpl() {
        dynamicService = RetrofitHelper.createApi(DynamicService.class);
    }

    @Override
    public void getDynamics(Subscriber<DynamicsData> subscriber, int page) {
        dynamicService.getDynamics(page)
                .compose(RxHelper.<DynamicsData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getDynamicDetail(Subscriber<DynamicBean> subscriber, String id) {
        dynamicService.getDynamicDetail(id)
                .compose(RxHelper.<DynamicBean>transform())
                .subscribe(subscriber);
    }

    @Override
    public void postDynamic(Subscriber<BaseBean> subscriber, String content, String video, String... image) {
        if (video != null) {
            dynamicService.postVideoDynamic(content, video, "1", image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else {
            dynamicService.postImageDynamic(content, video, image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

    }

    @Override
    public void addComment(Subscriber<BaseBean> subscriber, String id, String content) {
        dynamicService.addComment(id, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getComments(Subscriber<CommentData> subscriber, String id, int page) {
        dynamicService.getComments(id, page)
                .compose(RxHelper.<CommentData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void addLike(Subscriber<BaseBean> subscriber, String id) {
        dynamicService.addLike(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void cancelLike(Subscriber<BaseBean> subscriber, String id) {
        dynamicService.cancelLike(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getLikes(Subscriber<LikeData> subscriber, String id, int page) {
        dynamicService.getLikes(id, page)
                .compose(RxHelper.<LikeData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void reportDynamic(Subscriber<BaseBean> subscriber, String id, String type) {
        dynamicService.reportDynamic(id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deleteDynamic(Subscriber<BaseBean> subscriber, String id) {
        dynamicService.deleteDynamic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
