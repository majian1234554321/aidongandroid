package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.LoginModel;
import com.leyuan.aidong.ui.mvp.model.interfaces.LoginModelInterface;
import com.leyuan.aidong.ui.mvp.presenter.LoginPresenterInterface;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;

public class LoginPresenter implements LoginPresenterInterface {


    private Context context;
    private LoginViewInterface loginViewInterface;
    private LoginModelInterface loginModel;
//    private QQLogin qqLogin;

    public LoginPresenter(Context context, LoginViewInterface loginViewInterface) {
        this.context = context;
        this.loginViewInterface = loginViewInterface;
        loginModel = new LoginModel();

//        qqLogin = new QQLogin((Activity) context, thirdPartyLoginListener);
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

    public void onActivityResultData(int requestCode, int resultCode, Intent data) {

    }

    public void loginThirdParty(int loginMode) {

    }


}
