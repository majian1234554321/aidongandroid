package com.example.aidong.ui.mvp.presenter.impl;


import com.example.aidong .entity.model.result.LoginResult;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.UpdateDeviceService;
import com.example.aidong .utils.LogAidong;

import rx.Subscriber;

public class UpdateDevicePresenter {

    public void updateDevice(String device_token) {
        UpdateDeviceService service = RetrofitHelper.createApi(UpdateDeviceService.class);
        service.updateDevice("android", device_token)
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogAidong.i("updateDevice", e.toString());
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        LogAidong.i("onNext");
                    }
                });
    }
}
