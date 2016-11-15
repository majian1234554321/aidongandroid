package com.leyuan.aidong.ui.mvp.presenter;

public interface RegisterPresenterInterface {

    void regitserIdentify(String mobile, String imageIndentify);
    void foundIdentify(String mobile);
    void checkIdentify(String mobile, String code, String password);
    void bindingCaptcha( String mobile);
    void checkCaptchaImage( String mobile, String captcha);

}
