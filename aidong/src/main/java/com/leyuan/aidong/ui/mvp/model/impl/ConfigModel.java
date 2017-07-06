package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.ConfigUrlResult;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.ConfigService;

import rx.Observer;

/**
 * Created by user on 2017/7/5.
 */

public class ConfigModel {

    private ConfigService configService;

    public ConfigModel() {
//        this.configService = RetrofitHelper.createApi(ConfigService.class, UrlConfig.URL_CONFIG);
    }

    public void getUrlConfig(Observer<ConfigUrlResult> subscribe) {
        configService.getUrlConfig()
                .compose(RxHelper.<ConfigUrlResult>transform())
                .subscribe(subscribe);
    }
}
