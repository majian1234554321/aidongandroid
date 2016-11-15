package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.IdentifyService;
import com.leyuan.aidong.ui.mvp.model.interfaces.RegisterModelInterface;

import rx.Subscriber;

public class RegisterModel implements RegisterModelInterface {

    private IdentifyService mIdentifyService;

    public RegisterModel() {
        mIdentifyService = RetrofitHelper.createApi(IdentifyService.class);
    }

    @Override
    public void regitserIdentify(Subscriber<UserCoach> subscriber, String mobile) {
        mIdentifyService.regitserIdentify(mobile)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    public void foundIdentify(Subscriber<UserCoach> subscriber, String mobile) {
        mIdentifyService.foundIdentify(mobile)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    @Override
    public void bindingCaptcha(Subscriber<UserCoach> subscriber, String mobile) {
        mIdentifyService.bindingMobile(mobile)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    @Override
    public void checkIdentify(Subscriber<UserCoach> subscriber, String mobile, String code, String password) {

        mIdentifyService.checkIdentify(mobile, code, password)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);

    }

    @Override
    public void checkCaptchaImage(Subscriber<UserCoach> subscriber, String mobile, String captcha) {
        mIdentifyService.checkCaptchaImage(mobile,captcha)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);

    }
}
