package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.ConfigUrlResult;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.ConfigService;

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
