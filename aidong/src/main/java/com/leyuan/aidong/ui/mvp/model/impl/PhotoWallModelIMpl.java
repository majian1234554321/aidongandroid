package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.api.PhotoWallService;
import com.leyuan.aidong.ui.mvp.model.PhotoWallModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * photo wall
 * Created by song on 2017/2/20.
 */
public class PhotoWallModelIMpl implements PhotoWallModel{
    private PhotoWallService photoService;

    public PhotoWallModelIMpl() {
        photoService = RetrofitHelper.createApi(PhotoWallService.class);
    }

    @Override
    public void addPhotos(Subscriber<BaseBean> subscriber, String... photos) {
        photoService.addPhotos(photos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deletePhotos(Subscriber<BaseBean> subscriber, String id) {
        photoService.deletePhotos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
