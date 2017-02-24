package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.module.thirdpartylogin.ThirdLoginUtils;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.impl.LoginModel;
import com.leyuan.aidong.ui.mvp.model.LoginModelInterface;
import com.leyuan.aidong.ui.mvp.presenter.LoginPresenterInterface;
import com.leyuan.aidong.ui.mvp.view.LoginViewInterface;

public class LoginPresenter implements LoginPresenterInterface {

    private Context context;
    private LoginViewInterface loginViewInterface;
    private LoginModelInterface loginModel;
//    private QQLogin qqLogin;
    private ThirdLoginUtils thirdLoginUtils;

    public LoginPresenter(Activity context, LoginViewInterface loginViewInterface) {
        this.context = context;
        this.loginViewInterface = loginViewInterface;
        loginModel = new LoginModel();
        thirdLoginUtils = new ThirdLoginUtils(context,thirdLoginListner);

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
                loginViewInterface.loginResult(null);
            }

            @Override
            public void onNext(LoginResult user) {
                loginViewInterface.loginResult(user.getUser());
                App.mInstance.setUser(user.getUser());
            }
        }, accout, password);

    }

    public void loginThirdParty(int mode){
        thirdLoginUtils.loginThirdParty(mode);
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
                loginViewInterface.loginResult(null);
                //                loginViewInterface.loginResult(false);
            }

            @Override
            public void onNext(LoginResult user) {
                App.mInstance.setUser(user.getUser());
                loginViewInterface.loginResult(user.getUser());

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
        thirdLoginUtils.onActivityResult(requestCode,resultCode,data);
    }

    private final ThirdLoginUtils.OnThirdPartyLogin thirdLoginListner = new ThirdLoginUtils.OnThirdPartyLogin() {
        @Override
        public void onThridLogin(String sns, String code) {
            loginSns(sns,code);
        }
    };

}
