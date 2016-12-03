package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.module.thirdpartylogin.QQLogin;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.LoginModel;
import com.leyuan.aidong.ui.mvp.model.interfaces.LoginModelInterface;
import com.leyuan.aidong.ui.mvp.presenter.LoginPresenterInterface;
import com.leyuan.aidong.ui.mvp.presenter.LoginThirdPartyInterface;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;
import com.tencent.tauth.Tencent;

public class LoginPresenter implements LoginPresenterInterface {

    public static final int LOGIN_WEIXIN = 1;
    public static final int LOGIN_QQ = 2;
    public static final int LOGIN_WEIBO = 3;

    private Context context;
    private LoginViewInterface loginViewInterface;
    private LoginModelInterface loginModel;
    private QQLogin qqLogin;

    public LoginPresenter(Context context, LoginViewInterface loginViewInterface) {
        this.context = context;
        this.loginViewInterface = loginViewInterface;
        loginModel = new LoginModel();

        qqLogin = new QQLogin((Activity) context, thirdPartyLoginListener);
    }

    @Override
    public void login(String accout, String password) {
        loginModel.login(new BaseSubscriber<LoginResult>(context) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                                loginViewInterface.loginResult(false);
            }

            @Override
            public void onNext(LoginResult user) {
                loginViewInterface.loginResult(true);
                App.mInstance.setUser(user.getUser());
            }
        }, accout, password);

    }

    @Override
    public void loginSns(String sns, String access){
        loginModel.loginSns(new BaseSubscriber<LoginResult>(context) {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //                loginViewInterface.loginResult(false);
            }

            @Override
            public void onNext(LoginResult user) {
                loginViewInterface.loginResult(true);
                App.mInstance.setUser(user.getUser());
            }
        }, sns, access);

    }

    @Override
    public void autoLogin() {
        loginModel.autoLogin(new BaseSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult user) {
                App.mInstance.setUser(user.getUser());
            }
        });

    }

    @Override
    public void onActivityResultData(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data, qqLogin.getUiListener());
    }

    public void loginThirdParty(int loginMode) {
        switch (loginMode){
            case LOGIN_WEIXIN:
                break;
            case LOGIN_WEIBO:
                break;
            case LOGIN_QQ:
                break;
        }
    }

    private final LoginThirdPartyInterface thirdPartyLoginListener = new LoginThirdPartyInterface() {
        @Override
        public void onInvokeThridPartyComplete(String code) {

        }
    };
}
