package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .entity.user.CheckIdentifyResult;

import rx.Subscriber;

public interface RegisterModelInterface {
    void regitserIdentify(Subscriber<UserCoach> subscriber, String mobile);
    void foundIdentify(Subscriber<UserCoach> subscriber, String mobile);
    void bindingCaptcha(Subscriber<UserCoach> subscriber, String mobile);

    void bindingCaptchaSns(Subscriber<UserCoach> subscriber, String mobile, String profile_info, String type);

    void unbindingCaptcha(Subscriber<UserCoach> subscriber, String mobile);


    void checkIdentify(Subscriber<CheckIdentifyResult> subscriber, String token, String captcha, String password);

    void checkIdentifyRegister(Subscriber<CheckIdentifyResult> subscriber, String token, String captcha, String password);

    void checkCaptchaImage(Subscriber<UserCoach> subscriber, String mobile, String captcha);

//    void completeUserInfo(Subscriber<UserCoach> subscriber,Map<String ,String> params,String file);
}
