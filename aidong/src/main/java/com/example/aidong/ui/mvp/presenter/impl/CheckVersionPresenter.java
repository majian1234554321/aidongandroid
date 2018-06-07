package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.user.VersionResult;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.CheckVersion;
import com.example.aidong .http.subscriber.IsLoginSubscriber;

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
                .subscribe(new IsLoginSubscriber<VersionResult>(context) {
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
