package com.leyuan.aidong.ui.mvp.model;


import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.FastLoginService;

import rx.Subscriber;

public class FastLoginModel {

    private FastLoginService mService;

    public FastLoginModel() {
        mService = RetrofitHelper.createApi(FastLoginService.class);
    }

    public void getIdentify(Subscriber<LoginResult> subscriber, String mobile){
        mService.getIdentify(mobile)
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }

    public void fastLogin(Subscriber<LoginResult> subscriber, String mobile, String code){
        mService.checkIdentify(mobile,code)
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }
}
