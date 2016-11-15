package com.leyuan.aidong.ui.mvp.presenter;

public interface LoginPresenterInterface {

    void login(String accout, String password);
    void loginSns(String sns, String access);

    void autoLogin();
}
