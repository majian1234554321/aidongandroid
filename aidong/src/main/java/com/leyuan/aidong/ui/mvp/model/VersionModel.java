package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.VersionInformation;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.VersionService;
import com.leyuan.aidong.utils.Constant;

import rx.Subscriber;

/**
 * Created by user on 2017/3/6.
 */
public class VersionModel {
    VersionService service;

    public VersionModel() {
        service = RetrofitHelper.createApi(VersionService.class);
    }

    public void getVersionInfo(Subscriber<VersionInformation> subscriber) {
        service.getVersionInfo(Constant.OS_TYPE)
                .compose(RxHelper.<VersionInformation>transform())
                .subscribe(subscriber);
    }
}
