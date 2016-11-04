package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.user.User;
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
    public void regitserIdentify(Subscriber<User> subscriber, String mobile) {
        mIdentifyService.regitserIdentify(mobile)
                .compose(RxHelper.<User>transform())
                .subscribe(subscriber);
    }

    public void foundIdentify(Subscriber<User> subscriber, String mobile) {
        mIdentifyService.foundIdentify(mobile)
                .compose(RxHelper.<User>transform())
                .subscribe(subscriber);
    }

    @Override
    public void checkIdentify(Subscriber<User> subscriber, String mobile, String code, String password, String re_password) {

        mIdentifyService.checkIdentify(mobile, code, password, re_password)
                .compose(RxHelper.<User>transform())
                .subscribe(subscriber);

    }
}
