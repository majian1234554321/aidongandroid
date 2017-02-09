package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.model.UserCoach;

import rx.Subscriber;

public interface RegisterModelInterface {
    void regitserIdentify(Subscriber<UserCoach> subscriber, String mobile);
    void foundIdentify(Subscriber<UserCoach> subscriber, String mobile);
    void bindingCaptcha(Subscriber<UserCoach> subscriber, String mobile);

    void checkIdentify(Subscriber<UserCoach> subscriber, String token, String captcha, String password);
    void checkCaptchaImage(Subscriber<UserCoach> subscriber, String mobile, String captcha);

//    void completeUserInfo(Subscriber<UserCoach> subscriber,Map<String ,String> params,String file);
}
