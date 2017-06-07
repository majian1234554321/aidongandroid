package com.leyuan.aidong.ui.mvp.model.impl;


import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.LoginService;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.LoginModelInterface;

import rx.Subscriber;

public class LoginModel implements LoginModelInterface {

    private LoginService mLoginService;

    public LoginModel() {
        mLoginService = RetrofitHelper.createApi(LoginService.class);
    }


    @Override
    public void login(Subscriber<LoginResult> subscriber, String account, String password) {
//        String device_toke = SharePrefUtils.getString(App.mInstance.context, "regId", "");
//        LogAidong.i("Push MyReceiver", "device_toke regId = " + device_toke);
        mLoginService.login(account, password, App.getInstance().getjPushId())
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }

    @Override
    public void loginSns(Subscriber<LoginResult> subscriber, String sns, String access_token) {
        mLoginService.loginSns(sns, access_token, App.getInstance().getjPushId())
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }

    @Override
    public void autoLogin(Subscriber<LoginResult> subscriber) {
        mLoginService.autoLogin(App.getInstance().getjPushId())
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }

    @Override
    public void exitLogin(Subscriber<LoginResult> subscriber) {
        mLoginService.exitLogin()
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }
}
