package com.example.aidong.ui.mvp.presenter;

public interface RegisterPresenterInterface {

    void regitserIdentify(String mobile);

    void foundIdentify(String mobile);

    void checkIdentify(String mobile, String code, String password);

    void checkIdentifyBinding( String captcha);

    void checkIdentifyBindingSns(String captcha);

    void bindingCaptcha(String mobile);

    void bindingCaptchaSns(String mobile, String profile_info, String type);

    void unbindingCaptcha(String mobile);

    void checkCaptchaImage(String mobile, String captcha);

    String getBingdingMobile();

    void checkIdentifyRegister(String token, String code, String password);

    String getToken();
}
