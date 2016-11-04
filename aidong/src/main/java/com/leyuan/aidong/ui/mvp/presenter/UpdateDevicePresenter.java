package com.leyuan.aidong.ui.mvp.presenter;


import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.UpdateDeviceService;
import com.leyuan.aidong.utils.LogUtil;

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
                        LogUtil.i("updateDevice", e.toString());
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        LogUtil.i("onNext");
                    }
                });
    }
}
