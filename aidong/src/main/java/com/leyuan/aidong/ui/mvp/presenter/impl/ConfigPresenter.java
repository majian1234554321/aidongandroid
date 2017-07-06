package com.leyuan.aidong.ui.mvp.presenter.impl;

import com.leyuan.aidong.entity.ConfigUrlResult;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.ConfigModel;

/**
 * Created by user on 2017/7/5.
 */

public class ConfigPresenter {

    private ConfigModel configModel;

    public ConfigPresenter() {
        this.configModel = new ConfigModel();
    }

    public void getUrlConfig() {
        configModel.getUrlConfig(new BaseSubscriber<ConfigUrlResult>(null) {
            @Override
            public void onNext(ConfigUrlResult configUrlResult) {
//                 if(configUrlResult != null && configUrlResult.isDebug()){
//
//                 }
            }

            @Override
            public void onError(Throwable e) {
//                super.onError(e);
            }
        });
    }
}
