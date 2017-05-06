package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.VersionInformation;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.ui.mvp.model.VersionModel;
import com.leyuan.aidong.ui.mvp.view.VersionViewListener;

/**
 * Created by user on 2017/3/6.
 */
public class VersionPresenterImpl {
    private Context context;
    private VersionViewListener listener;
    private VersionModel model;

    public VersionPresenterImpl(Context context, VersionViewListener listener) {
        this.context = context;
        this.listener = listener;
        model = new VersionModel();
    }

    public void getVersionInfo() {
        model.getVersionInfo(new IsLoginSubscriber<VersionInformation>(context) {
            @Override
            public void onNext(VersionInformation versionInfomation) {
                listener.onGetVersionInfo(versionInfomation);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.onGetVersionInfo(null);
            }
        });
    }
}
