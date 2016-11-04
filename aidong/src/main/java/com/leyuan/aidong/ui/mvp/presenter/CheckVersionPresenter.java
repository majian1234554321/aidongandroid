package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;

import com.leyuan.aidong.entity.user.VersionResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CheckVersion;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;

public class CheckVersionPresenter {

    private Context context;
    private OnResult mOnResult;
    private CheckVersion service;

    public CheckVersionPresenter(Context context, OnResult onResult) {
        this.context = context;
        mOnResult = onResult;
        service = RetrofitHelper.createApi(CheckVersion.class);
    }

    public void CheckVersion() {
        service.checkVersion()
                .compose(RxHelper.<VersionResult>transform())
                .subscribe(new BaseSubscriber<VersionResult>(context) {
                    @Override
                    public void onNext(VersionResult versionResult) {
                        mOnResult.onResult(versionResult);
                    }
                });

    }

    public interface OnResult {
        void onResult(VersionResult result);
    }

}
