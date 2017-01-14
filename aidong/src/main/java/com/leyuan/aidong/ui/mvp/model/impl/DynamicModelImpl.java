package com.leyuan.aidong.ui.mvp.model.impl;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CommentData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.DynamicService;
import com.leyuan.aidong.ui.mvp.model.DynamicModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DynamicModelImpl implements DynamicModel{
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
    public void postDynamic(Subscriber<BaseBean> subscriber, String content, String video, String... image) {
        dynamicService.postDynamic(content,video,image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void addComment(Subscriber<BaseBean> subscriber, String id, String content) {
        dynamicService.addComment(id,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getComments(Subscriber<CommentData> subscriber, String id, int page) {
        dynamicService.getComments(id,page)
                .compose(RxHelper.<CommentData>transform())
                .subscribe(subscriber);
    }
}
