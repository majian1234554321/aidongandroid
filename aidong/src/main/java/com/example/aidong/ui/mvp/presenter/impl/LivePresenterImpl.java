package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.video.LiveHomeResult;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .ui.mvp.model.impl.VideoModelImpl;
import com.example.aidong .ui.mvp.view.LiveHomeView;

/**
 * Created by user on 2017/3/2.
 */

public class LivePresenterImpl {
    private Context context;
    private LiveHomeView viewListener;
    private VideoModelImpl model;

    public LivePresenterImpl(Context context, LiveHomeView viewListener) {
        this.context = context;
        this.viewListener = viewListener;
        model = new VideoModelImpl();
    }

    public void getLiveHome() {
        model.getHomeLives(new IsLoginSubscriber<LiveHomeResult.LiveHome>(context) {
            @Override
            public void onNext(LiveHomeResult.LiveHome liveHome) {
                viewListener.onGetLiveHomeData(liveHome);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                viewListener.onGetLiveHomeData(null);
            }
        });
    }

    public void livePlayStatistics(String id) {
        model.livePlayStatistics(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {

            }
        }, id);
    }

}
