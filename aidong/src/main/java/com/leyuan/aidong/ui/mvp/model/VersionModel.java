package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.user.VersionResult;
import com.leyuan.aidong.http.RetrofitVersionHelper;
import com.leyuan.aidong.http.api.VersionService;
import com.leyuan.aidong.utils.Constant;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 2017/3/6.
 */
public class VersionModel {
    VersionService service;

    public VersionModel() {
        service = RetrofitVersionHelper.createVersionApi(VersionService.class);
    }

    public void getVersionInfo(Subscriber<VersionResult> subscriber) {
        service.getVersionInfo(Constant.OS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

//    public VersionModel() {
//        service = RetrofitHelper.createApi(VersionService.class);
//    }
//
//    public void getVersionInfo(Subscriber<VersionInformation> subscriber) {
//        service.getVersionInfo(Constant.OS_TYPE)
//                .compose(RxHelper.<VersionInformation>transform())
//                .subscribe(subscriber);
//    }
}
