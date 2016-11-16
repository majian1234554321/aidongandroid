package com.leyuan.aidong.ui.mvp.view;

public interface RegisterViewInterface {

    void getIdentifyCode(boolean success);

    void register(boolean success);

    void checkCaptchaImage(boolean success, String mobile);
}
