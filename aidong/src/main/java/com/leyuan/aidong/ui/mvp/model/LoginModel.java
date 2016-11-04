package com.leyuan.aidong.ui.mvp.model;


import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.LoginService;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.interfaces.LoginModelInterface;
import com.leyuan.aidong.utils.LogUtil;
import com.leyuan.aidong.utils.SharePrefUtils;

import rx.Subscriber;

public class LoginModel implements LoginModelInterface {

    private LoginService mLoginService;

    public LoginModel() {
        mLoginService = RetrofitHelper.createApi(LoginService.class);
    }


    @Override
    public void login(Subscriber<LoginResult> subscriber, String account, String password) {
        String device_toke = SharePrefUtils.getString(App.mInstance.context, "regId", "");
        LogUtil.i("Push MyReceiver", "device_toke regId = " + device_toke);
        mLoginService.login(account, password, "android", device_toke)
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }

    @Override
    public void autoLogin(Subscriber<LoginResult> subscriber) {
        mLoginService.autoLogin()
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }
}
