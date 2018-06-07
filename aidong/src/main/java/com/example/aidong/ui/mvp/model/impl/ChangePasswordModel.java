package com.example.aidong.ui.mvp.model.impl;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.api.ChangePasswordService;
import com.example.aidong .ui.mvp.model.ChangePasswordInterface;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangePasswordModel implements ChangePasswordInterface {

    private ChangePasswordService mService;

    public ChangePasswordModel() {
        mService = RetrofitHelper.createApi(ChangePasswordService.class);
    }


    @Override
    public void changePassword(Subscriber<BaseBean> subscriber, String password, String new_password, String re_passsword) {
        mService.changePassword(password, new_password, re_passsword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
