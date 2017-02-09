package com.leyuan.aidong.ui.mvp.model.impl;


import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.ChangePasswordService;
import com.leyuan.aidong.ui.mvp.model.ChangePasswordInterface;

import rx.Subscriber;

public class ChangePasswordModel implements ChangePasswordInterface {

    private ChangePasswordService mService;

    public ChangePasswordModel() {
        mService = RetrofitHelper.createApi(ChangePasswordService.class);
    }


    @Override
    public void changePassword(Subscriber<LoginResult> subscriber, String password, String new_password, String re_passsword) {
        mService.changePassword(password, new_password, re_passsword)
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
    }
}
