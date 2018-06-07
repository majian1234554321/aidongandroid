package com.example.aidong.ui.mvp.presenter.impl;

import com.example.aidong .entity.ConfigUrlResult;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .ui.mvp.model.impl.ConfigModel;

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
