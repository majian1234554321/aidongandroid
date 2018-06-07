package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.api.PhotoWallService;
import com.example.aidong .ui.mvp.model.PhotoWallModel;

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
