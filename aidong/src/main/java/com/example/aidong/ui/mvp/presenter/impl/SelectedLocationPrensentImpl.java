package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.data.DiscoverVenuesData;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.impl.DiscoverModelImpl;
import com.example.aidong .ui.mvp.view.SelectedLocationView;
import com.example.aidong .widget.SwitcherLayout;

/**
 * Created by user on 2018/1/30.
 */
public class SelectedLocationPrensentImpl {

    private final Context context;
    private final SelectedLocationView listener;
    DiscoverModelImpl model;

    public SelectedLocationPrensentImpl(Context context, SelectedLocationView listener) {
        this.context = context;
        this.listener = listener;
        model = new DiscoverModelImpl();
    }

    public void refreshVenuesNearly(int page) {
        model.getVenues(new ProgressSubscriber<DiscoverVenuesData>(context) {
            @Override
            public void onNext(DiscoverVenuesData discoverVenuesData) {
                listener.onRefreshData(discoverVenuesData.getGym());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.onRefreshData(null);
            }
        }, App.lat, App.lon, page);
    }

    public void refreshVenuesNearly(final SwitcherLayout switcherLayout, int page) {
        model.getVenues(new BaseSubscriber<DiscoverVenuesData>(context) {
            @Override
            public void onStart() {
                switcherLayout.showLoadingLayout();
            }

            @Override
            public void onCompleted() {
                switcherLayout.showContentLayout();
            }

            @Override
            public void onNext(DiscoverVenuesData discoverVenuesData) {
                if (discoverVenuesData != null && discoverVenuesData.getGym() != null && !discoverVenuesData.getGym().isEmpty()) {
                    switcherLayout.showContentLayout();
                    listener.onRefreshData(discoverVenuesData.getGym());
                } else {
                    switcherLayout.showEmptyLayout();
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                switcherLayout.showExceptionLayout();
            }
        }, App.lat, App.lon, page);
    }


    public void getVenuesNearlyMore(int page) {
        model.getVenues(new ProgressSubscriber<DiscoverVenuesData>(context) {
            @Override
            public void onNext(DiscoverVenuesData discoverVenuesData) {
                listener.onGetMoreData(discoverVenuesData.getGym());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, App.lat, App.lon, page);
    }

}
