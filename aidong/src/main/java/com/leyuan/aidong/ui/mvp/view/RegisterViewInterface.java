package com.leyuan.aidong.ui.mvp.view;

public interface RegisterViewInterface {

    void onGetIdentifyCode(boolean success);

    void register(boolean success);

    void onCheckCaptchaImageResult(boolean success, String mobile);

    void onRequestStart();
}
