package com.leyuan.aidong.ui.mvp.model;


import com.leyuan.aidong.entity.model.result.LoginResult;

import rx.Subscriber;

public interface LoginModelInterface {

    void login(Subscriber<LoginResult> subscriber, String count, String password);
    void loginSns(Subscriber<LoginResult> subscriber, String sns, String access_token);

    void autoLogin(Subscriber<LoginResult> subscriber);
}
