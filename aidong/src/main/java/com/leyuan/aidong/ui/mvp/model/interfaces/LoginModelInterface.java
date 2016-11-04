package com.leyuan.aidong.ui.mvp.model.interfaces;


import com.leyuan.aidong.entity.model.result.LoginResult;

import rx.Subscriber;

public interface LoginModelInterface {

    void login(Subscriber<LoginResult> subscriber, String count, String password);

    void autoLogin(Subscriber<LoginResult> subscriber);
}
