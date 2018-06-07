package com.example.aidong.ui.mvp.presenter;

import android.content.Intent;

public interface LoginPresenterInterface {

    void login(String accout, String password);
    void loginSns(String sns, String access);

    void autoLogin();

    void exitLogin();

    void onActivityResultData(int requestCode, int resultCode, Intent data);
}
