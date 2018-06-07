package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.SystemBean;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.SystemService;
import com.example.aidong .ui.mvp.model.SystemModel;

import rx.Subscriber;

/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public class SystemModelImpl implements SystemModel{
    private SystemService systemService;

    public SystemModelImpl() {
        systemService = RetrofitHelper.createApi(SystemService.class);
    }

    @Override
    public void getSystem(Subscriber<SystemBean> subscriber, String os) {
        systemService.getSystemInfo(os)
                .compose(RxHelper.<SystemBean>transform())
                .subscribe(subscriber);
    }

}
